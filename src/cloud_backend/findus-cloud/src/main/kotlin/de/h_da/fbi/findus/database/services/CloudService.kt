package de.h_da.fbi.findus.database.services

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import de.h_da.fbi.findus.database.models.MedicalExamination
import de.h_da.fbi.findus.database.repository.MedicalExaminationRepository
import de.h_da.fbi.findus.entities.timeOffset
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.math.BigInteger
import java.nio.file.InvalidPathException
import java.nio.file.Paths
import java.time.LocalDate

/**
 * CloudService service this service handles data that should be saved into the db.
 * It validates data and calls the fitting repository to perform the requested database operation.
 *
 * @constructor Creates a cloud service that can be called to handle db-operations
 */
class CloudService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: MedicalExaminationRepository = MedicalExaminationRepository(client)
    private val logger = KotlinLogging.logger {}
    private val invalidMedicalExaminationMessage: String =
        "the medical examination argument does not have a valid format"
    private val mongoCommunicationError: String =
        "an error occurred while communicating with db Error: "
    private val oldestAllowedDate: LocalDate = LocalDate.of(1975, 1, 1)
    private val minAge = 0
    private val invalidFileMessage: String =
        "the medical examination argument files do not have a valid format"


    /**
     * gets all currently stored MedicalExaminations from the db
     * @return List<MedicalExamination> the found check-up
     * @throws MongoException if there was an error with the db communication
     */
    fun getAllExaminations(): List<MedicalExamination> {
        try {
            return (repo.findAll()).sortedWith(compareBy<MedicalExamination> { it.patientIdentifier }.thenBy { it._id })
        } catch (exception: MongoException) {
            logger.error(exception) { mongoCommunicationError + exception }
            throw exception
        }
    }

    /**
     * inserts a MedicalExamination into the database
     * @param examination the MedicalExamination to insert into the database
     * @return String the id if insert operation was successful, else exception
     * @throws MongoException when the insert operation was not successful
     * @throws IllegalArgumentException when the medical examination data is invalid
     */
    fun createMedicalExamination(examination: MedicalExamination): String {
        try {
            when (proveExamination(examination)) {
                true -> return repo.addOne(examination)
                else -> throw IllegalArgumentException(invalidMedicalExaminationMessage)
            }
        } catch (exception: IllegalArgumentException) {
            logger.error(exception) { "The check-up parameter has an invalid format: $exception" }
            throw exception
        } catch (exception: MongoException) {
            logger.error(exception) { "The path is invalid, Error: $exception" }
            throw exception
        }
    }

    /**
     * UploadCheckUpImages load all images of a MedicalExamination element into the db
     *
     * @param loadedExamination the MedicalExamination that holds the images to upload
     * @return true if upload was successful, else false
     * @throws IllegalArgumentException when the MedicalExamination data images are invalid
     */
    fun uploadExaminationImages(loadedExamination: MedicalExamination): Boolean {
        return if (isValidPath(loadedExamination)) repo.uploadImages(loadedExamination) else throw IllegalArgumentException(
            invalidFileMessage
        )
    }

    /**
     * Deletes all MedicalExaminations
     *
     * @return Boolean true if delete operation succeeded, else false
     */
    fun deleteAllCheckUps(): Boolean {
        return repo.deleteAll()
    }

    /**
     * gets a MedicalExamination with the given id
     * @param id the id of the MedicalExamination that should be searched for
     * @return MedicalExamination if found or null if not
     * @throws IllegalArgumentException if the requested id is not in a valid format
     * @throws MongoException if there was an error with the db communication
     */
    fun getExaminationById(id: String): MedicalExamination? {
        (try {
            return repo.getById(id)
        } catch (exception: MongoException) {
            logger.error(exception) { mongoCommunicationError + exception }
            throw exception
        })
    }

    /**
     * Checks if the given MedicalExamination is valid
     *
     * @param examination theMedicalExamination that should get validated
     * @return true if validation was successful, else false
     */
    private fun proveExamination(examination: MedicalExamination): Boolean {
        val currentExaminationDate = examination.examinationDate
        return when {
            !(repo.isValidId(examination._id)) -> false
            examination.patientIdentifier == "" -> false
            !isValidDate(examination.birthDate) -> false
            !(isValidInt(examination.age)) -> false
            examination.animalBreed == "" -> false
            currentExaminationDate == null -> true
            currentExaminationDate < examination.birthDate -> false
            else -> true
        }
    }

    /**
     * Is valid date checks if the given date is in a valid date range
     *
     * @param examinationDate the date that should be checked
     * @return true if date is in a valid range, else false
     */
    private fun isValidDate(examinationDate: LocalDate?): Boolean {
        val today =
            Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset)).toJavaLocalDateTime()
                .toLocalDate()
        return when {
            examinationDate == null -> true
            examinationDate < oldestAllowedDate -> false
            examinationDate > today -> false
            else -> true
        }
    }

    /**
     * Validates an integer parameter
     *
     * @param integerToCheck the Integer that should get validated
     * @return Boolean true if Integer has a valid value, else false
     */
    private fun isValidInt(integerToCheck: Int): Boolean {
        return when {
            integerToCheck < minAge -> false
            integerToCheck >= Integer.MAX_VALUE -> false
            else -> true
        }
    }

    /**
     * Anonymize data by converting string to bytearray to bigInteger and then back to a numerical representation of the bigInteger
     *
     * @param stringToAnonymize the string that should be anonymized
     * @return the anonym string
     */
    fun anonymizeData(stringToAnonymize: String): String {
        val byteArray = stringToAnonymize.toByteArray(de.h_da.fbi.findus.routes.charset)
        return BigInteger(byteArray).toString()
    }

    /**
     * checks if all MedicalExamination picture paths are valid
     * @param examination the MedicalExamination with the pictures to check-up
     * @return Boolean true if all paths are valid or false if not
     */
    private fun isValidPath(examination: MedicalExamination): Boolean {
        examination.pictures.forEach { picture ->
            when {
                picture.isAbsolute -> return false
                else -> {
                    try {
                        Paths.get(picture.name)
                    } catch (exception: InvalidPathException) {
                        logger.error(exception) { "The path is invalid, Error: $exception" }
                        return false
                    } catch (exception: NullPointerException) {
                        logger.error(exception) { "The path is invalid, Error: $exception" }
                        return false
                    }
                }
            }
        }
        return true
    }

}
