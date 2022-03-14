package de.h_da.fbi.findus.routes

import com.mongodb.MongoException
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.path.normal.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.throws
import de.h_da.fbi.findus.database.models.MedicalExamination
import de.h_da.fbi.findus.database.services.CloudService
import de.h_da.fbi.findus.entities.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import mu.KotlinLogging
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.mockito.Mockito
import java.io.BufferedWriter
import java.io.File

const val invalidData = "Invalid data!"
const val errorWhileSavingToDatabase = "Error while saving the medical examination to the database!"
const val errorLoadingFromDatabase =
    "Error while loading the medical examination from the database!"
const val errorWritingFile = "Error while preparing the output csv file!"
const val errorInvalidArgument = "the posted medical examination element is not valid"
const val errorExportingCSV = "could not create csv data "
const val errorWhileLoadingFromDatabase = "could not load saved medical examination entries Error: "
val oldestAllowedDate: LocalDate = LocalDate(1975, 1, 1)
const val minAge = 0
const val exportFileName = "exportData.csv"
const val iteratorOffset = 1
const val nullSize = 0
const val CSVColumnHeaderID = "id"
const val CSVColumnHeaderName = "patientIdentifier"
const val CSVColumnHeaderBirthdate = "birthdate"
const val CSVColumnHeaderAge = "age"
const val CSVColumnHeaderAnimalBreed = "animalBreed"
const val CSVColumnHeaderExaminationDate = "examinationDate"
const val CSVColumnHeaderExistingDisease = "existingDisease"
const val CSVColumnHeaderBodyPosture = "bodyPosture"
const val CSVColumnHeaderBreathMovement = "breathMovement"
const val CSVColumnHeaderSymptom = "symptom_X"
const val CSVColumnHeaderMovement = "movement_X"
const val CSVColumnHeaderMuscle = "muscle_X"
const val CSVColumnHeaderMarker = "P"
const val delimiter = ","
const val exampleId = "exampleId"
const val therapyTextExample = "your therapy..."
const val timeOffset = "UTC+1"
const val cloudRouteWelcomeMessage = "Welcome to the findus cloud service"
val charset = Charsets.UTF_8
const val errorWhileUpdatingInDatabase =
    "Error while updating the medical examination in the database!"
const val therapyText = "your therapy plan"
const val malformedExaminationData = "Malformed examination or invalid!"
const val downloadRouteDescription =
    "This route returns all MedicalExaminations as a CSV file ordered and grouped by the patientIdentifier"
const val downloadRouteSummary = "Get all MedicalExaminations as a CSV file"
const val diagnoseRouteDescription = "This route returns an AI-diagnose for the given description"
const val diagnoseRouteSummary =
    "Get a diagnose for a patient based on the posted id and description"
const val uploadPictureRouteDescription =
    "This route can be used to upload a picture of a patient to the cloud. Therefore, the patientIdentifier of the patient is required"
const val uploadPictureRouteSummary = "Upload a picture of an examination to the cloud"
const val uploadRouteDescription =
    "This route can be used to upload a patient that will be stored as an anonymized MedicalExamination"
const val uploadRouteSummary = "Upload a MedicalExamination object to the cloud"
const val welcomeRouteDescription =
    "This route can be used to receive a welcome message from the api"
const val welcomeRouteSummary = "Get a welcome message from the api"

//routes
const val examination = "/examination"
const val uploadRoute = "$examination/upload"
const val downloadRoute = "$examination/download"
const val uploadPictureRoute = "$uploadRoute/picture/{id}/name/{pictureName}"
const val diagnoseRoute = "$examination/diagnose"
const val welcomeRoute = ""


class InvalidData(errorMessage: String) : Exception(errorMessage)
class DatabaseException(errorMessage: String) : Exception(errorMessage)
class NotFound(notFoundMessage: String) : Exception(notFoundMessage)
class CsvIoException(ioExceptionMessage: String) : Exception(ioExceptionMessage)


/**
 * default implementation of DiagnoseService interface
 */
class DiagnoseServiceImpl : DiagnoseService

fun pictureAdded(medicalExaminationId: String): String {
    return "Picture added to patient $medicalExaminationId successful!"
}

fun malformedId(malformedMedicalExaminationId: String?): String {
    return "Malformed medical examination id: $malformedMedicalExaminationId"
}

fun noExaminationWithId(medicalExaminationId: String?): String {
    return "No patient with id $medicalExaminationId!"
}

fun diagnoseResponseText(diagnoseText: String): String {
    return diagnoseText
}


fun NormalOpenAPIRoute.cloudRoute() {

    val patientPersistenceService = CloudService()
    val logger = KotlinLogging.logger {}
    route(welcomeRoute) {
        get<Unit, WelcomeResponse>(
            info(
                summary = welcomeRouteSummary,
                description = welcomeRouteDescription
            ),
            example = WelcomeResponse.EXAMPLE,
        ) {
            respond(WelcomeResponse(cloudRouteWelcomeMessage))
        }

        route(uploadRoute) {
            throws(
                HttpStatusCode.InternalServerError,
                StatusResponse(errorWhileSavingToDatabase),
                { exception: DatabaseException -> exception.message?.let { StatusResponse(it) } }) {
                throws(
                    HttpStatusCode.BadRequest,
                    StatusResponse(invalidData),
                    { exception: InvalidData -> exception.message?.let { StatusResponse(it) } }) {
                    post<Unit, MedicalExaminationSuccessful, MedicalExaminationAPI>(
                        info(
                            summary = uploadRouteSummary,
                            description = uploadRouteDescription
                        ),
                        exampleRequest = MedicalExaminationAPI.EXAMPLE,
                        exampleResponse = MedicalExaminationSuccessful.EXAMPLE

                    ) { _, medicalExaminationAPI ->
                        run {
                            val medicalExaminationAPICreate = MedicalExamination(
                                //anonymize patient-identifier
                                patientIdentifier = patientPersistenceService.anonymizeData(medicalExaminationAPI.patientIdentifier),
                                birthDate = medicalExaminationAPI.birthDate.toJavaLocalDateTime()
                                    .toLocalDate(),
                                age = medicalExaminationAPI.age,
                                animalBreed = medicalExaminationAPI.animalBreed,
                                examinationDate = medicalExaminationAPI.examinationDate.toJavaLocalDateTime()
                                    .toLocalDate(),
                                existingDisease = medicalExaminationAPI.existingDisease,
                                bodyMarker = medicalExaminationAPI.bodyMarker,
                                muscleCircumference = medicalExaminationAPI.muscleCircumference,
                                movement = medicalExaminationAPI.movement,
                                bodyPosture = medicalExaminationAPI.bodyPosture,
                                breathMovement = medicalExaminationAPI.breathMovement,
                                symptoms = medicalExaminationAPI.symptoms,
                            )
                            if (validateReceivedBaseData(medicalExaminationAPICreate)) {
                                val patientId = try {
                                    patientPersistenceService.createMedicalExamination(
                                        medicalExaminationAPICreate
                                    )
                                } catch (exception: IllegalArgumentException) {
                                    logger.error(exception) { errorInvalidArgument + exception }
                                    throw InvalidData(malformedExaminationData)
                                } catch (exception: MongoException) {
                                    logger.error(exception) { errorWhileSavingToDatabase + exception }
                                    throw DatabaseException(errorWhileSavingToDatabase)
                                }
                                respond(MedicalExaminationSuccessful(patientId))
                            } else {
                                throw InvalidData(malformedExaminationData)
                            }
                        }
                    }
                }
            }
        }
        route(diagnoseRoute) {
            throws(
                HttpStatusCode.BadRequest,
                StatusResponse(invalidData),
                { exception: InvalidData -> exception.message?.let { StatusResponse(it) } }) {

                post<Unit, StatusResponse, DiagnoseInput>(
                    info(
                        summary = diagnoseRouteSummary,
                        description = diagnoseRouteDescription
                    ),
                    exampleRequest = DiagnoseInput.EXAMPLE,
                    exampleResponse = StatusResponse(diagnoseResponseText(therapyTextExample))
                ) { _, diagnoseInput ->
                    val description = diagnoseInput.description
                    val inputId = diagnoseInput.id
                    if (description == "" || inputId == "") {
                        throw InvalidData(malformedExaminationData)
                    }
                    val mockDiagnoseService =
                        Mockito.mock(DiagnoseServiceImpl::class.java)
                    Mockito.`when`(mockDiagnoseService.requestDiagnose(true))
                        .thenReturn(therapyText)
                    val textToSend = mockDiagnoseService.requestDiagnose(true)
                    respond(StatusResponse(diagnoseResponseText(textToSend)))
                }
            }
        }
        route(downloadRoute) {
            throws(
                HttpStatusCode.InternalServerError,
                StatusResponse(errorLoadingFromDatabase),
                { exception: DatabaseException -> exception.message?.let { StatusResponse(it) } }) {

                throws(
                    HttpStatusCode.BadRequest,
                    StatusResponse(invalidData),
                    { exception: InvalidData -> exception.message?.let { StatusResponse(it) } })
                throws(
                    HttpStatusCode.InternalServerError,
                    StatusResponse(errorWritingFile),
                    { exception: CsvIoException -> exception.message?.let { StatusResponse(it) } }) {
                    get<Unit, ExportFile>(
                        info(
                            summary = downloadRouteSummary,
                            description = downloadRouteDescription
                        ),
                        example = ExportFile.EXAMPLE
                    ) {
                        try {
                            val foundMedicalExaminations =
                                patientPersistenceService.getAllExaminations()

                            //load the max size for each array element -> this sets the amount of required columns in the CSV-file
                            val maxMarkerSize =
                                (foundMedicalExaminations.map { it.bodyMarker.size }).maxOrNull()
                                    ?: nullSize
                            val maxMuscleCircumferenceSize =
                                (foundMedicalExaminations.map { it.muscleCircumference.size }).maxOrNull()
                                    ?: nullSize
                            val maxMovementSize =
                                (foundMedicalExaminations.map { it.movement.size }).maxOrNull()
                                    ?: nullSize
                            val maxSymptomsSize =
                                (foundMedicalExaminations.map { it.symptoms.size }).maxOrNull()
                                    ?: nullSize
                            val listMaxMarkerHeaders = mutableListOf<String>().apply {
                                repeat(maxMarkerSize) { this.add(element = "$CSVColumnHeaderMarker${it + iteratorOffset}") }
                            }
                            val maxMuscleCircumferenceHeaders =
                                mutableListOf<String>().apply {
                                    repeat(maxMuscleCircumferenceSize) { this.add(element = "$CSVColumnHeaderMuscle${it + iteratorOffset}") }
                                }
                            val listMaxMovementHeaders = mutableListOf<String>().apply {
                                repeat(maxMovementSize) { this.add(element = "$CSVColumnHeaderMovement${it + iteratorOffset}") }
                            }
                            val listMaxSymptomHeaders = mutableListOf<String>().apply {
                                repeat(maxSymptomsSize) { this.add(element = "$CSVColumnHeaderSymptom${it + iteratorOffset}") }
                            }
                            //set the column headers dynamically based on the array sizes
                            val headerList = arrayOf(
                                CSVColumnHeaderID,
                                CSVColumnHeaderName,
                                CSVColumnHeaderBirthdate,
                                CSVColumnHeaderAge,
                                CSVColumnHeaderAnimalBreed,
                                CSVColumnHeaderExaminationDate,
                                CSVColumnHeaderExistingDisease,
                                *listMaxMarkerHeaders.map { it }.toTypedArray(),
                                *maxMuscleCircumferenceHeaders.map { it }.toTypedArray(),
                                *listMaxMovementHeaders.map { it }.toTypedArray(),
                                CSVColumnHeaderBodyPosture,
                                CSVColumnHeaderBreathMovement,
                                *listMaxSymptomHeaders.map { it }.toTypedArray()
                            )
                            val writer = File(exportFileName).bufferedWriter()
                            val csvFormat: CSVFormat = CSVFormat.Builder.create()
                                .setHeader(*headerList)
                                .setAllowMissingColumnNames(true)
                                .build()

                            val csvPrinter = openReader(writer, csvFormat)


                            // fill one row in the CSV-file by iterating over each saved MedicalExaminationAPI document
                            foundMedicalExaminations.forEach { currentElement ->
                                val listOfMarkers =
                                    generateOutputList(
                                        currentElement.bodyMarker,
                                        maxMarkerSize
                                    )
                                val listOfMuscles = generateOutputList(
                                    currentElement.muscleCircumference,
                                    maxMuscleCircumferenceSize
                                )
                                val listOfMovement =
                                    generateOutputList(
                                        currentElement.movement,
                                        maxMovementSize
                                    )
                                val listOfSymptoms =
                                    generateOutputList(
                                        currentElement.symptoms,
                                        maxSymptomsSize
                                    )
                                csvPrinter.printRecord(
                                    currentElement._id,
                                    currentElement.patientIdentifier,
                                    currentElement.birthDate.toString(),
                                    currentElement.age,
                                    currentElement.animalBreed,
                                    currentElement.examinationDate,
                                    currentElement.existingDisease.joinToString(delimiter),
                                    *listOfMarkers.map { it }.toTypedArray(),
                                    *listOfMuscles.map { it }.toTypedArray(),
                                    *listOfMovement.map { it }.toTypedArray(),
                                    currentElement.bodyPosture,
                                    currentElement.breathMovement,
                                    *listOfSymptoms.map { it }.toTypedArray(),
                                )
                            }

                            closeReader(csvPrinter)
                            val returnFile = File(exportFileName)
                            returnFile.deleteOnExit()
                            respond(
                                ExportFile(
                                    returnFile.inputStream()
                                )
                            )
                        } catch (exception: IllegalArgumentException) {
                            logger.error(exception) { errorInvalidArgument + exception }
                            throw InvalidData(malformedExaminationData)
                        } catch (exception: MongoException) {
                            logger.error(exception) { errorWhileSavingToDatabase + exception }
                            throw DatabaseException(errorWhileSavingToDatabase)
                        } catch (exception: IOException) {
                            logger.error(exception) { errorExportingCSV + exception }
                            throw CsvIoException(errorExportingCSV)
                        }
                    }
                }
            }
        }
        // This route cannot be used until the open-api file upload bug is resolved.
        // When it is resolved, the CloudImageRoute in the file CloudImageRoute.kt can be deleted and
        // replaced with this route
        route(uploadPictureRoute) {
            route("only-for-documentation") {
                throws(
                    HttpStatusCode.InternalServerError,
                    StatusResponse(errorWhileSavingToDatabase),
                    { exception: DatabaseException -> exception.message?.let { StatusResponse(it) } }) {

                    throws(
                        HttpStatusCode.BadRequest,
                        StatusResponse(invalidData),
                        { exception: InvalidData -> exception.message?.let { StatusResponse(it) } }) {

                        throws(
                            HttpStatusCode.NotFound,
                            StatusResponse(noExaminationWithId(exampleId)),
                            { exception: NotFound -> exception.message?.let { StatusResponse(it) } }) {

                            post<ExaminationIdAndPictureName, StatusResponse, ExaminationPicture>(
                                info(
                                    summary = uploadPictureRouteSummary,
                                    description = uploadPictureRouteDescription
                                ),
                                exampleRequest = ExaminationPicture.EXAMPLE,
                                exampleResponse = StatusResponse(pictureAdded(exampleId))
                            ) { examinationIdAndName, examinationPicture ->
                                run {
                                    val loadedExamination = try {
                                        patientPersistenceService.getExaminationById(
                                            examinationIdAndName.id
                                        )
                                    } catch (exception: IllegalArgumentException) {
                                        logger.error(exception) { "could not load medical examination Error: $exception" }
                                        throw InvalidData(malformedId(examinationIdAndName.id))
                                    } catch (exception: MongoException) {
                                        throw DatabaseException(errorWhileLoadingFromDatabase)
                                    }
                                        ?: throw NotFound(noExaminationWithId(examinationIdAndName.id))
                                    val file =
                                        File(examinationIdAndName.pictureName)
                                    file.writeBytes(examinationPicture.picture.readBytes())
                                    file.deleteOnExit()

                                    val pictures = loadedExamination.pictures.toMutableList()
                                    pictures.add(file)
                                    loadedExamination.pictures = pictures.toList()

                                    val updateResult = try {
                                        patientPersistenceService.uploadExaminationImages(
                                            loadedExamination
                                        )
                                    } catch (exception: IllegalArgumentException) {
                                        logger.error(exception) { errorInvalidArgument + exception }
                                        throw InvalidData(malformedExaminationData)
                                    }
                                    if (!updateResult) {
                                        throw DatabaseException(errorWhileUpdatingInDatabase)
                                    }
                                    respond(StatusResponse(pictureAdded(loadedExamination._id)))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun closeReader(csvPrinter: CSVPrinter) = withContext(IO) {
    async(IO, CoroutineStart.DEFAULT) {
        csvPrinter.flush()
        csvPrinter.close()
    }.await()
}

private suspend fun openReader(writer: BufferedWriter, csvFormat: CSVFormat): CSVPrinter =
    withContext(IO) {
        val printer: CSVPrinter = async(IO, CoroutineStart.DEFAULT) {
            return@async CSVPrinter(writer, csvFormat)
        }.await()
        return@withContext printer
    }


/**
 * Validates a received MedicalExaminationAPI element
 *
 * @param examinationToCheck the MedicalExaminationAPI element that should get validated
 * @return true if MedicalExaminationAPI element has valid content
 */
fun validateReceivedBaseData(examinationToCheck: MedicalExamination): Boolean {
    val today = Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset)).toJavaLocalDateTime()
        .toLocalDate()
    val examinationDate = examinationToCheck.examinationDate
    return when {
        examinationToCheck.patientIdentifier == welcomeRoute -> false
        examinationToCheck.birthDate.isBefore(oldestAllowedDate.toJavaLocalDate()) -> false
        examinationToCheck.birthDate > today -> false
        examinationToCheck.age < minAge -> false
        examinationToCheck.animalBreed == welcomeRoute -> false
        examinationDate == null -> true
        examinationDate < examinationToCheck.birthDate -> false
        else -> true
    }
}

/**
 * Generates a list of strings from a given list. If the inputList is smaller than the quantity parameter,
 * the remaining elements will be null
 *
 * @param T the type of the input list
 * @param listToProcess the input list
 * @param quantity the size the output list should habe
 * @return a list of strings with the content form the inputList
 */
fun <T> generateOutputList(
    listToProcess: List<T>,
    quantity: Int
): MutableList<String?> {
    val listOfStringsToGenerate = mutableListOf<String?>().apply {
        repeat(quantity) {
            (listToProcess.getOrElse(it) { null }).let { currentElement ->
                this.add(
                    element = currentElement.toString()
                )
            }
        }
    }
    return listOfStringsToGenerate
}
