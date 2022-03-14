package de.h_da.fbi.findus.database.services

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import de.h_da.fbi.findus.database.models.*
import de.h_da.fbi.findus.database.repository.PatientRepository
import mu.KotlinLogging
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File
import java.nio.file.InvalidPathException
import java.nio.file.Paths
import java.time.LocalDate

/**
 * Patient service this service handles data that should be saved into the db.
 * It validates data and calls the fitting repository to perform the requested database operation.
 *
 * @constructor Creates a patient service that can be called to handle db-operations
 */
class PatientService : KoinComponent {
    private val client: MongoClient by inject()
    private val repo: PatientRepository = PatientRepository(client)
    private val logger = KotlinLogging.logger {}
    private val notAllowedCharacters: String = "[<({^=\$!|})?*+.>]"
    private val invalidIdMessage: String = "the requested id does not have a valid format"
    private val invalidPatientMessage: String = "the patient argument does not have a valid format"
    private val mongoCommunicationError: String =
        "an error occurred while communicating with db Error: "

    /**
     * gets a patient with the given id
     * @param id the id of the Patient that should be searched for
     * @return Patient if found or null if not
     * @throws IllegalArgumentException if the requested id is not in a valid format
     * @throws MongoException if there was an error with the db communication
     */
    fun getPatientById(id: String): Patient? {
        when (repo.isValidId(id)) {
            true -> (try {
                return repo.getById(id)
            } catch (exception: MongoException) {
                logger.error(exception) { mongoCommunicationError + exception }
                throw exception
            })
            false -> throw IllegalArgumentException(invalidIdMessage)
        }
    }


    /**
     * gets all currently stored patients from the db
     * @return List<Patient> the found patients
     * @throws MongoException if there was an error with the db communication
     */
    fun getAllPatients(): List<Patient> {
        try {
            return repo.findAll()
        } catch (exception: MongoException) {
            logger.error(exception) { mongoCommunicationError + exception }
            throw exception
        }
    }

    /**
     * gets all patients with the given name
     * @param name the name of the patient(s) to search for
     * @return List<Patient> all patients with the name to search for
     * @throws MongoException if there was an error with the db communication
     */
    fun getPatientByName(name: String): List<Patient> {
        try {
            return repo.getByName(validateString(name))
        } catch (exception: MongoException) {
            logger.error(exception) { mongoCommunicationError + exception }
            throw exception
        }
    }

    /**
     * inserts a patient into the database
     * @param patient the patient to insert into the database
     * @return String the id if insert operation was successful, else exception
     * @throws MongoException when the insert operation was not successful
     * @throws IllegalArgumentException when the patient data is invalid
     */
    fun createPatient(patient: Patient): String {
        try {
            when (checkPatient(patient)) {
                true -> return repo.addOne(patient)
                else -> throw IllegalArgumentException(invalidPatientMessage)
            }
        } catch (exception: IllegalArgumentException) {
            logger.error(exception) { "The patient parameter has an invalid format: $exception" }
            throw exception
        } catch (exception: MongoException) {
            logger.error(exception) { "The path is invalid, Error: $exception" }
            throw exception
        }
    }

    /**
     * inserts multiple Patients into the database
     * @param patients List of type Patient to insert into the database
     * @return List<String> the inserted ids if the patients were successfully inserted
     * @throws IllegalArgumentException if the requested patients have an invalid format
     * @throws MongoException if there was an error with the db communication
     */
    fun createMultiplePatients(patients: List<Patient>): List<String> {
        when (checkPatients(patients)) {
            true -> {
                try {
                    return repo.addMany(patients)
                } catch (exception: MongoException) {
                    logger.error(exception) { "Failed to create multiple patients, Error: $exception" }
                    throw exception
                }
            }
            else -> throw IllegalArgumentException(invalidPatientMessage)
        }
    }

    /**
     * takes a patient that should get modified and updates the representation of the patient in the database
     * @param patient the patient to update
     * @return Boolean true if the update was successful or false if not
     * @throws IllegalArgumentException if the requested patient has an invalid format
     */
    fun updatePatient(patient: Patient): Boolean {
        return if (checkPatient(patient)) repo.updateOne(patient) else throw IllegalArgumentException(
            invalidPatientMessage
        )
    }

    /**
     * takes multiple patients that should get modified and updates the representation of the patients in the database
     * @param patients the patients to update
     * @return Boolean true if the updates were successful or false if not
     * @throws IllegalArgumentException if the requested patients have an invalid format
     */
    fun updateMultiplePatients(patients: List<Patient>): Boolean {
        return if (checkPatients(patients)) repo.updateMany(patients) else throw IllegalArgumentException(
            invalidPatientMessage
        )
    }

    /**
     * deletes a patient with the given id
     * @param patientId the id of the Patient to delete
     * @return Boolean true if delete was successful or false if not
     * @throws IllegalArgumentException if the patientId does have an invalid style
     */
    fun deletePatientById(patientId: String): Boolean {
        return if (repo.isValidId(patientId)) repo.deleteOne(patientId) else throw IllegalArgumentException(
            invalidIdMessage
        )
    }

    /**
     * deletes multiple patients with the list of ids
     * @param patientIds the ids of patients to delete
     * @return Boolean true if deletes were successful or false if not
     * @throws IllegalArgumentException if the requested ids have an invalid format
     */
    fun deleteMultiplePatientsByIds(patientIds: List<String>): Boolean {
        patientIds.forEach { currentId ->
            if (!repo.isValidId(currentId)) {
                throw IllegalArgumentException(invalidIdMessage)
            }
        }
        return repo.deleteMany(patientIds)
    }

    /**
     * Deletes all patients
     *
     * @return Boolean true if delete operation succeeded, else false
     */
    fun deleteAllPatients(): Boolean {
        return repo.deleteAll()
    }


    /**
     * Checks if the given patient(s) are valid
     *
     * @param patients the patients that should get validated
     * @return true if validation was successful, else false
     */
    private fun checkPatients(patients: List<Patient>): Boolean {
        if (patients.isEmpty()) return false
        patients.forEach { currentPatient ->
            when (checkPatient(currentPatient)) {
                true -> {
                }
                false -> return false
            }
        }
        return true
    }

    /**
     * Checks if the given patient is valid
     *
     * @param patient the patients that should get validated
     * @return true if validation was successful, else false
     */
    private fun checkPatient(patient: Patient): Boolean {
        return isValidPath(patient) && repo.isValidId(patient._id) &&
                isValidFloat(patient.weight) && (patient.name.isNotEmpty())
    }


    /**
     * Validates a float parameter
     *
     * @param floatValue the Float that should get validated
     * @return Boolean true if Float has a valid value, else false
     */
    private fun isValidFloat(floatValue: Float): Boolean {
        return when {
            floatValue <= 0.0f -> false
            floatValue >= Float.MAX_VALUE -> false
            else -> true
        }
    }

    /**
     * checks if all patient picture paths are valid
     * @param patient the patient with the pictures to check
     * @return Boolean true if all paths are valid or false if not
     */
    private fun isValidPath(patient: Patient): Boolean {
        patient.pictures.forEach { picture ->
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

    /**
     * Validates a string by removing special characters
     *
     * @param stringToValidate the string that should get validated
     * @return the string in a valid format
     */
    private fun validateString(stringToValidate: String): String {
        val regex = Regex(notAllowedCharacters)
        return regex.replace(stringToValidate, "")
    }

    /**
     * Creates 10 default dummy patients that can be used for presentation purposes
     *
     * @return true if insert operation was successful, else false
     */
    fun createDummyData(): Boolean {

        try {
            deleteAllPatients()
        } catch (exception: Exception) {
            logger.error(exception) { "Could not delete all patients, Error: $exception" }
        }

        if (repo.countEntries() == 0L) {
            try {
                val dummyList = mutableListOf<Patient>()

                val zoePicture = loadImage("zoe.jpg")
                val milliePicture = loadImage("puppy.jpg")
                val baluPicture = loadImage("labrador.jpg")
                val maliPicture = loadImage("shepherd.jpg")
                val mogliPicture = loadImage("bernese_mountaindog.jpg")
                val zappyPicture = loadImage("zappy.jpg")
                val luluPicture = loadImage("lulu.jpg")
                val bowserPicture = loadImage("bowser.jpg")
                val peziPicture = loadImage("pezi.jpg")
                val pixelPicture = loadImage("pixel.jpg")

                val patient10 = Patient(
                    name = "Zoe",
                    weight = 18.0f,
                    pictures = listOf(zoePicture),
                    comment = "Wird nicht gerne gestreichelt",
                    sex = Sex.FEMALE,
                    birthDate = LocalDate.of(2020, 4, 10).toString(),
                    species = "Smooth Collie",
                    ownerName = "Susanne Weber",
                    ownerAddress = "Mönchhofstraße 18",
                    ownerPhoneNumber = "123456789",
                    diagnostic = "Magenverstimmung",
                    diagnosticSymptoms = listOf("Erbrechen", "Müdigkeit", "Durchfall"),
                    diagnosticNotes = listOf("Hat irgendetwas im Wald gefressen"),
                    chartDataWeight =
                    listOf(
                        ChartData("01.01.2022", 18.0f),
                        ChartData("07.01.2022", 17.8f),
                        ChartData("07.01.2022", 17.6f),
                        ChartData("14.01.2022", 18.0f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 1.0f),
                        ChartData("M. triceps brachii", 1.5f),
                        ChartData("M. brachialis", 2.5f),
                        ChartData("M. trapezius", 4.0f),
                        ChartData("M. omotransversarius", 1.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 8).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "gereizt durch häufiges erbrechen",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.ISOECHOGEN,
                                    comment = "",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 2,
                                    tensionIntensity = 2
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "7",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                )
                            )
                        )
                    )
                )

                val patient9 = Patient(
                    name = "Balu",
                    weight = 28.0f,
                    pictures = listOf(baluPicture),
                    comment = "",
                    sex = Sex.MALE,
                    birthDate = LocalDate.of(2019, 4, 19).toString(),
                    species = "Labrador",
                    ownerName = "Alexander Geber",
                    ownerAddress = "Kirchstrasse 17",
                    ownerPhoneNumber = "0157776689",
                    diagnostic = "Hangbeinlahmheit",
                    diagnosticSymptoms = listOf("Schmerzen Rücken", "Gelenkathrose"),
                    diagnosticNotes = listOf("Bei nächsten Termin muss wieder Blut genommen werden"),
                    chartDataWeight =
                    listOf(
                        ChartData("01.01.2021", 27.0f),
                        ChartData("10.01.2021", 27.5f),
                        ChartData("19.01.2021", 28.0f),
                        ChartData("30.01.2021", 28.2f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 3.0f),
                        ChartData("M. triceps brachii", 2.7f),
                        ChartData("M. brachialis", 4.2f),
                        ChartData("M. trapezius", 1.2f),
                        ChartData("M. omotransversarius", 0.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 28).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.ANECHOGEN,
                                    comment = "muss beobachtet werden",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "auffällige Blutwerte",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "auffällige Blutwerte",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 1,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "6",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "5",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "9",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                )
                            )
                        )
                    )
                )

                val patient8 = Patient(
                    name = "Mali",
                    weight = 15.0f,
                    pictures = listOf(maliPicture),
                    comment = "Getreideallergie",
                    sex = Sex.FEMALE,
                    birthDate = LocalDate.of(2018, 3, 14).toString(),
                    species = "Shepherd",
                    ownerName = "Lukas Kranich",
                    ownerAddress = "Kirchgasse 11",
                    ownerPhoneNumber = "01577568940",
                    diagnostic = "Getreideallergie",
                    diagnosticSymptoms = listOf("Erbrechen", "Ausschlag"),
                    diagnosticNotes = listOf("Ausschlag und erbrechen oft nach Getreidehaltigem Futter"),
                    chartDataWeight =
                    listOf(
                        ChartData("01.01.2022", 9.4f),
                        ChartData("07.01.2022", 10.0f),
                        ChartData("07.01.2022", 10.2f),
                        ChartData("14.01.2022", 10.4f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 1.4f),
                        ChartData("M. triceps brachii", 2.0f),
                        ChartData("M. brachialis", 0.6f),
                        ChartData("M. trapezius", 0.8f),
                        ChartData("M. omotransversarius", 1.1f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 14).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Gereizt durch erbrechen",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.ANECHOGEN,
                                    comment = "",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.ISOECHOGEN,
                                    comment = "",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.CHANGED,
                                    comment = "",
                                    type = Type.GREEN,
                                    checkedStateInjury = true,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 1,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "12",
                                    painIntensity = 1,
                                    tensionIntensity = 2
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "3",
                                    painIntensity = 3,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "8",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "9",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                )
                            )
                        )
                    )
                )

                val patient7 = Patient(
                    name = "Mogli",
                    weight = 18.0f,
                    pictures = listOf(mogliPicture),
                    comment = "",
                    sex = Sex.MALE,
                    birthDate = LocalDate.of(2017, 5, 19).toString(),
                    species = "Bernese Mountain Dog",
                    ownerName = "Anna Wirsing",
                    ownerAddress = "Heidelbergerstrasse 78a",
                    ownerPhoneNumber = "0157766445",
                    diagnostic = "Salter-Harris-Fraktur",
                    diagnosticSymptoms = listOf("Humpelt", "Tritt nicht mehr richtig auf"),
                    diagnosticNotes = listOf("Bruch nach Sprung über einen Baumstamm im Wald"),
                    chartDataWeight =
                    listOf(
                        ChartData("04.04.2021", 18.0f),
                        ChartData("17.06.2021", 17.8f),
                        ChartData("18.09.2021", 17.6f),
                        ChartData("20.09.2021", 18.0f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 4.0f),
                        ChartData("M. triceps brachii", 1.5f),
                        ChartData("M. brachialis", 0.9f),
                        ChartData("M. trapezius", 3.1f),
                        ChartData("M. omotransversarius", 1.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 20).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.CHANGED,
                                    comment = "Muskelschwund durch Bruch",
                                    type = Type.RED,
                                    checkedStateInjury = true,
                                    checkedStateSwelling = true,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 2,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "5",
                                    painIntensity = 1,
                                    tensionIntensity = 2
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 2,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 3,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "6",
                                    painIntensity = 1,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "9",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                )
                            )
                        )
                    )
                )

                val patient6 = Patient(
                    name = "Millie",
                    weight = 19.0f,
                    pictures = listOf(milliePicture),
                    comment = "Oft sehr aufgeregt beim Tierarztbesuch",
                    sex = Sex.FEMALE,
                    birthDate = LocalDate.of(2017, 11, 18).toString(),
                    species = "Border Collie",
                    ownerName = "Karin Heliger",
                    ownerAddress = "Dossenheimerlandstrasse 22",
                    ownerPhoneNumber = "015775567745",
                    diagnostic = "Entzündung des Dickdarms",
                    diagnosticSymptoms = listOf("Erbrechen", "Müdigkeit", "Durchfall"),
                    diagnosticNotes = listOf(
                        "Hat irgendetwas im Wald gefressen",
                        "Ultraschall des Dickdarms beim nächsten Termin"
                    ),
                    chartDataWeight =
                    listOf(
                        ChartData("01.01.2022", 17.2f),
                        ChartData("07.01.2022", 16.2f),
                        ChartData("07.01.2022", 17.0f),
                        ChartData("14.01.2022", 17.5f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 1.0f),
                        ChartData("M. triceps brachii", 1.5f),
                        ChartData("M. brachialis", 2.7f),
                        ChartData("M. trapezius", 1.5f),
                        ChartData("M. omotransversarius", 1.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 19).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Magen stark verkleinert",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.ANECHOGEN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.HYPERECHOGEN,
                                    comment = "stark entzündet",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "6",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "12",
                                    painIntensity = 3,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "9",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "7",
                                    painIntensity = 0,
                                    tensionIntensity = 3
                                )
                            )
                        )
                    )
                )

                val patient5 = Patient(
                    name = "Lulu",
                    weight = 14.0f,
                    pictures = listOf(luluPicture),
                    comment = "Angst vor lauten Geraeuschen",
                    sex = Sex.FEMALE,
                    birthDate = LocalDate.of(2015, 12, 18).toString(),
                    species = "Yorkshire Terrier",
                    ownerName = "Özlem Türeci",
                    ownerAddress = "Gartenstraße 129",
                    ownerPhoneNumber = "01732521415",
                    diagnostic = "Hautreizung am Bauch",
                    diagnosticSymptoms = listOf("Unruhe", "Müdigkeit", "Durchfall"),
                    diagnosticNotes = listOf(
                        "Unklare Hautiritation am Unterbauch",
                        "Allergietest beim nächsten Besuch"
                    ),
                    chartDataWeight =
                    listOf(
                        ChartData("05.01.2022", 13.2f),
                        ChartData("11.01.2022", 12.2f),
                        ChartData("12.01.2022", 11.0f),
                        ChartData("20.01.2022", 16.5f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 4.0f),
                        ChartData("M. triceps brachii", 2.5f),
                        ChartData("M. brachialis", 6.7f),
                        ChartData("M. trapezius", 6.5f),
                        ChartData("M. omotransversarius", 2.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2021, 12, 18).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Magen stark verkleinert",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "stark entzündet",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "null",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 2,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "12",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "1",
                                    painIntensity = 2,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 1,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "5",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                )
                            )
                        )
                    )
                )

                val patient4 = Patient(
                    name = "Bowser",
                    weight = 34.0f,
                    pictures = listOf(bowserPicture),
                    comment = "sehr ängstlich",
                    sex = Sex.MALE,
                    birthDate = LocalDate.of(2019, 11, 11).toString(),
                    species = "Australian Shepherd",
                    ownerName = "Frauke Manz",
                    ownerAddress = "Laubenstraße 24",
                    ownerPhoneNumber = "0162325214",
                    diagnostic = " Bauch ist aufgebläht und schmerzempfindlich",
                    diagnosticSymptoms = listOf("Erbrechen", "Durchfall"),
                    diagnosticNotes = listOf("Erbrechen", "Ultraschall durchführen"),
                    chartDataWeight =
                    listOf(
                        ChartData("01.01.2022", 12.2f),
                        ChartData("12.01.2022", 22.2f),
                        ChartData("15.01.2022", 31.0f),
                        ChartData("24.01.2022", 26.5f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 5.0f),
                        ChartData("M. triceps brachii", 3.5f),
                        ChartData("M. brachialis", 7.7f),
                        ChartData("M. trapezius", 2.5f),
                        ChartData("M. omotransversarius", 1.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 23).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Magen stark verkleinert",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "stark entzündet",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "8",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "3",
                                    painIntensity = 3,
                                    tensionIntensity = 0
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "5",
                                    painIntensity = 0,
                                    tensionIntensity = 2
                                ),
                                FieldValue(
                                    idOfField = "7",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "1",
                                    painIntensity = 3,
                                    tensionIntensity = 2
                                )
                            )
                        )
                    )
                )

                val patient3 = Patient(
                    name = "Zappy",
                    weight = 24.0f,
                    pictures = listOf(zappyPicture),
                    comment = "sehr aufgeregt",
                    sex = Sex.MALE,
                    birthDate = LocalDate.of(2018, 3, 14).toString(),
                    species = "Husky",
                    ownerName = "Peter Lohmeier",
                    ownerAddress = "Fritz-Walter-Str. 22",
                    ownerPhoneNumber = "015122352144",
                    diagnostic = "krampfartiger Husten",
                    diagnosticSymptoms = listOf("Fieber", "Müdigkeit", "Apathie"),
                    diagnosticNotes = listOf(
                        "Unklare Hautiritation am Unterbauch",
                        "Allergietest beim nächsten Besuch"
                    ),
                    chartDataWeight =
                    listOf(
                        ChartData("02.01.2022", 13.2f),
                        ChartData("11.01.2022", 12.2f),
                        ChartData("12.01.2022", 17.0f),
                        ChartData("20.01.2022", 26.5f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 4.0f),
                        ChartData("M. triceps brachii", 2.5f),
                        ChartData("M. brachialis", 6.7f),
                        ChartData("M. trapezius", 6.5f),
                        ChartData("M. omotransversarius", 2.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2022, 1, 15).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Magen stark verkleinert",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "stark entzündet",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "7",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                ),
                                FieldValue(
                                    idOfField = "6",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "11",
                                    painIntensity = 4,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "9",
                                    painIntensity = 0,
                                    tensionIntensity = 3
                                )
                            )
                        )
                    )
                )

                val patient2 = Patient(
                    name = "Pezi",
                    weight = 24.0f,
                    pictures = listOf(peziPicture),
                    comment = "",
                    sex = Sex.MALE,
                    birthDate = LocalDate.of(2017, 9, 19).toString(),
                    species = "Corgi",
                    ownerName = "Heidi Immer",
                    ownerAddress = "Karl-Meister Str. 22",
                    ownerPhoneNumber = "01745521112",
                    diagnostic = "Schnittwunde an der Schnauze",
                    diagnosticSymptoms = listOf("Fieber", "Müdigkeit", "Apathie"),
                    diagnosticNotes = listOf(
                        "Schnittwunde im Bereich der Schnauze",
                        "Verbandwechsel und Medikamentengabe"
                    ),
                    chartDataWeight =
                    listOf(
                        ChartData("01.01.2022", 11.2f),
                        ChartData("31.01.2022", 22.2f),
                        ChartData("15.01.2022", 13.0f),
                        ChartData("02.01.2022", 26.5f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 5.0f),
                        ChartData("M. triceps brachii", 4.5f),
                        ChartData("M. brachialis", 6.2f),
                        ChartData("M. trapezius", 7.5f),
                        ChartData("M. omotransversarius", 1.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2021, 12, 12).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Magen stark verkleinert",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.ANECHOGEN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.HYPERECHOGEN,
                                    comment = "stark entzündet",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "6",
                                    painIntensity = 1,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "1",
                                    painIntensity = 2,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "4",
                                    painIntensity = 2,
                                    tensionIntensity = 2
                                )
                            )
                        )
                    )
                )

                val patient1 = Patient(
                    name = "Pixel",
                    weight = 24.0f,
                    pictures = listOf(pixelPicture),
                    comment = "frisst sehr wenig",
                    sex = Sex.MALE,
                    birthDate = LocalDate.of(2015, 6, 7).toString(),
                    species = "French Bulldog",
                    ownerName = "Peter Lohmeier",
                    ownerAddress = "Fritz-Walter-Str. 22",
                    ownerPhoneNumber = "015122352144",
                    diagnostic = "antriebslos und müde",
                    diagnosticSymptoms = listOf("Müdigkeit", "Durchfall"),
                    diagnosticNotes = listOf(
                        "Eventuelle Unverträglichkeit auf Getreide",
                        "Blutabnahme durchführen"
                    ),
                    chartDataWeight =
                    listOf(
                        ChartData("05.01.2022", 13.2f),
                        ChartData("11.01.2022", 12.2f),
                        ChartData("12.01.2022", 11.0f),
                        ChartData("20.01.2022", 16.5f)
                    ),
                    chartDataMuscle =
                    listOf(
                        ChartData("M. latissimus dorsi", 4.0f),
                        ChartData("M. triceps brachii", 2.5f),
                        ChartData("M. brachialis", 6.7f),
                        ChartData("M. trapezius", 6.5f),
                        ChartData("M. omotransversarius", 2.9f)
                    ),
                    anamnese = listOf(
                        Anamnese(
                            id = "1",
                            date = LocalDate.of(2021, 11, 13).toString(),
                            organs = listOf(
                                Organ(
                                    name = "Speiseröhre",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Magen",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "Magen stark verkleinert",
                                    type = Type.GREEN
                                ),
                                Organ(
                                    name = "Dünndarm",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                Organ(
                                    name = "Dickdarm",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.HYPERECHOGEN,
                                    comment = "stark entzündet",
                                    type = Type.RED
                                ),
                                Organ(
                                    name = "Bauchspeicheldrüse",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Leber",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Galle",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Linke Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Rechte Niere",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment ="",
                                    type = Type.UNKNOWN
                                ),
                                Organ(
                                    name = "Blase",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                ),
                            ),
                            muscles = listOf(
                                Muscle(
                                    name = "M. latissimus dorsi",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. triceps brachii",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. brachialis",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                                Muscle(
                                    name = "M. omotransversarius",
                                    status = Status.UNKNOWN,
                                    comment = "",
                                    type = Type.UNKNOWN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = false
                                ),
                            )
                        )
                    ),
                    bodyMaps = listOf(
                        BodyMap(
                            "1", listOf(
                                FieldValue(
                                    idOfField = "1",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                )
                            )
                        ),
                        BodyMap(
                            "2", listOf(
                                FieldValue(
                                    idOfField = "2",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                ),
                                FieldValue(
                                    idOfField = "5",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                FieldValue(
                                    idOfField = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 1
                                )
                            )
                        )
                    )
                )

                dummyList.add(patient1)
                dummyList.add(patient2)
                dummyList.add(patient3)
                dummyList.add(patient4)
                dummyList.add(patient5)
                dummyList.add(patient6)
                dummyList.add(patient7)
                dummyList.add(patient8)
                dummyList.add(patient9)
                dummyList.add(patient10)

                createMultiplePatients(dummyList)

                return true
            } catch (exception: Exception) {
                logger.error(exception) { "The data is invalid, Error: $exception" }
            }

        }

        return false
    }

    private fun loadImage(imageName: String): File {
        val file = File("./$imageName")
        this::class.java.getResourceAsStream("/images/$imageName").use { inputStream ->
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    }

}
