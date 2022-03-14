package de.h_da.fbi.findus.routes

import com.mongodb.MongoException
import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.auth.*
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.throws
import de.h_da.fbi.findus.auth.JwtUser
import de.h_da.fbi.findus.database.models.*
import de.h_da.fbi.findus.database.services.PatientService
import de.h_da.fbi.findus.entities.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

const val invalidData = "Invalid data!"
const val malformedPatientData = "Malformed patient data!"
const val errorWhileLoadingFromDatabase = "Error while loading patients from the database!"
const val errorWhileSavingToDatabase = "Error while saving the patient to the database!"
const val errorWhileUpdatingInDatabase = "Error while updating the patient in the database!"
const val errorWhileDeletingInDatabase = "Error while deleting the patient in the database!"
const val minBodyMapId = 1
const val minAnamneseId = 1

fun malformedId(malformedPatientId: String?): String {
    return "Malformed patient id: $malformedPatientId"
}

fun noPatientWithId(patientId: String?): String {
    return "No patient with id $patientId!"
}

fun pictureAdded(patientId: String): String {
    return "Picture added to patient $patientId successful!"
}

fun patientDeleted(patientId: String?): String {
    return "Patient $patientId deleted successful!"
}

fun patientUpdated(patientId: String?): String {
    return "Patient $patientId updated successful!"
}

fun patientHasNoPictures(patientId: String?): String {
    return "Patient $patientId has no pictures!"
}

fun patientHasOnlyNPictures(patientId: String?, picturesAmount: Int?): String {
    return "Patient $patientId has only $picturesAmount picture(s)!"
}

fun malformedOrNotFoundIdOfBodyMap(malformedBodyMapId: String?): String {
    return "Malformed or not found bodyMap id: $malformedBodyMapId"
}

fun bodyMapDeleted(bodyMapId: String?): String {
    return "BodyMap $bodyMapId deleted successful!"
}

fun bodyMapUpdated(bodyMapId: String?): String {
    return "BodyMap $bodyMapId updated successful!"
}

fun anamneseIdNotFound(anamneseId: String?): String {
    return "Anamnese id $anamneseId not found!"
}

fun anamneseDeleted(anamneseId: String?): String {
    return "Anamnese $anamneseId deleted successful!"
}

fun anamneseUpdated(anamneseId: String?): String {
    return "Anamnese $anamneseId updated successful!"
}

class InvalidData(errorMessage: String) : Exception(errorMessage)
class DatabaseException(errorMessage: String) : Exception(errorMessage)
class NotFound(notFoundMessage: String) : Exception(notFoundMessage)

fun OpenAPIAuthenticatedRoute<JwtUser>.patientRoute() {

    val patientPersistenceService = PatientService()

    route("patient") {

        throws(
            HttpStatusCode.InternalServerError,
            StatusResponse(errorWhileSavingToDatabase),
            { exception: DatabaseException -> StatusResponse(exception.message) }) {

            throws(
                HttpStatusCode.BadRequest,
                StatusResponse(invalidData),
                { exception: InvalidData -> StatusResponse(exception.message) }) {

                /**
                 * Create a new patient
                 */
                post<Unit, PatientCreateSuccessful, PatientCreate, JwtUser>(
                    info(
                        summary = "Create patient",
                        description = "Creates a new patient with the given parameters."
                    ),
                    exampleRequest = PatientCreate.EXAMPLE,
                    exampleResponse = PatientCreateSuccessful.EXAMPLE

                ) { _, patientCreate ->

                    run {

                        // required parameters
                        val patient = Patient(
                            name = patientCreate.name,
                            weight = patientCreate.weight,
                            pictures = listOf(),
                            comment = patientCreate.comments,
                            sex = patientCreate.sex,
                            birthDate = patientCreate.birthDate,
                            species = patientCreate.species,
                            ownerName = patientCreate.ownerName,
                            ownerAddress = patientCreate.ownerAddress,
                            ownerPhoneNumber = patientCreate.ownerPhoneNumber,
                        )

                        // optional parameters
                        if (patientCreate.diagnostic != null) {
                            patient.diagnostic = patientCreate.diagnostic!!
                        }
                        if (patientCreate.diagnosticSymptoms != null) {
                            patient.diagnosticSymptoms = patientCreate.diagnosticSymptoms!!
                        }
                        if (patientCreate.diagnosticNotes != null) {
                            patient.diagnosticNotes = patientCreate.diagnosticNotes!!
                        }
                        val chartDataWeight = mutableListOf<ChartData>()
                        if (patientCreate.chartDataWeight != null) {
                            patientCreate.chartDataWeight!!.forEach { chartData ->
                                chartDataWeight.add(ChartData(chartData.key, chartData.value))
                            }
                            patient.chartDataWeight = chartDataWeight
                        }
                        val chartDataMuscle = mutableListOf<ChartData>()
                        if (patientCreate.chartDataMuscle != null) {
                            patientCreate.chartDataMuscle!!.forEach { chartData ->
                                chartDataMuscle.add(ChartData(chartData.key, chartData.value))
                            }
                            patient.chartDataMuscle = chartDataMuscle
                        }

                        val patientId = try {
                            patientPersistenceService.createPatient(patient)
                        } catch (exception: IllegalArgumentException) {
                            throw InvalidData(malformedPatientData)
                        } catch (exception: MongoException) {
                            throw DatabaseException(errorWhileSavingToDatabase)
                        }

                        respond(PatientCreateSuccessful(patientId))

                    }

                }

                /**
                 * Get all patients
                 */
                get<Unit, List<ApiPatient>, JwtUser>(
                    info(
                        summary = "Get all",
                        description = "Get a list of all patients."
                    ),
                    example = ApiPatient.EXAMPLE_LIST
                ) {

                    run {

                        val apiPatientList = mutableListOf<ApiPatient>()

                        try {
                            patientPersistenceService.getAllPatients().forEach { patient: Patient ->
                                run {
                                    val apiBodyMaps = mutableListOf<ApiBodyMap>()
                                    patient.bodyMaps.map { currentBodymap ->
                                        val apiFieldValues = mutableListOf<ApiFieldValues>()
                                        currentBodymap.fieldValues.map { currentFieldValues ->
                                            apiFieldValues.add(
                                                ApiFieldValues(
                                                    id = currentFieldValues.idOfField,
                                                    painIntensity = currentFieldValues.painIntensity,
                                                    tensionIntensity = currentFieldValues.tensionIntensity
                                                )
                                            )
                                        }
                                        apiBodyMaps.add(
                                            ApiBodyMap(
                                                currentBodymap.id,
                                                apiFieldValues
                                            )
                                        )
                                    }

                                    val apiChartDataWeight = mutableListOf<ApiChartData>()
                                    patient.chartDataWeight.forEach { chartData ->
                                        apiChartDataWeight.add(
                                            ApiChartData(
                                                chartData.key,
                                                chartData.value
                                            )
                                        )
                                    }

                                    val apiChartDataMuscle = mutableListOf<ApiChartData>()
                                    patient.chartDataMuscle.forEach { chartData ->
                                        apiChartDataMuscle.add(
                                            ApiChartData(
                                                chartData.key,
                                                chartData.value
                                            )
                                        )
                                    }
                                    val apiAnamnese = mutableListOf<ApiAnamnese>()
                                    patient.anamnese.forEach { anamnese ->
                                        val apiOrgans = mutableListOf<ApiOrgan>()
                                        val apiMuscles = mutableListOf<ApiMuscle>()
                                        anamnese.organs.forEach { organ ->
                                            apiOrgans.add(
                                                ApiOrgan(
                                                    name = organ.name,
                                                    status = organ.status,
                                                    statusChanged = organ.statusChanged,
                                                    comment = organ.comment?:"",
                                                    type = organ.type,
                                                )
                                            )
                                        }
                                        anamnese.muscles.forEach { muscle ->
                                            apiMuscles.add(
                                                ApiMuscle(
                                                    name = muscle.name,
                                                    status = muscle.status,
                                                    comment = muscle.comment,
                                                    type = muscle.type,
                                                    checkedStateInjury = muscle.checkedStateInjury,
                                                    checkedStateSwelling = muscle.checkedStateSwelling,
                                                    checkedStateHematoma = muscle.checkedStateHematoma
                                                )
                                            )
                                        }
                                        apiAnamnese.add(
                                            ApiAnamnese(
                                                anamnese.id,
                                                anamnese.date,
                                                apiOrgans,
                                                apiMuscles,
                                            )
                                        )
                                    }

                                    val apiPatient = ApiPatient(
                                        patient._id,
                                        patient.name,
                                        patient.weight,
                                        patient.comment,
                                        patient.sex,
                                        patient.birthDate,
                                        patient.species,
                                        patient.ownerName,
                                        patient.ownerAddress,
                                        patient.ownerPhoneNumber,
                                        patient.diagnostic,
                                        patient.diagnosticSymptoms,
                                        patient.diagnosticNotes,
                                        apiChartDataWeight,
                                        apiChartDataMuscle,
                                        apiAnamnese,
                                        apiBodyMaps,
                                    )
                                    apiPatientList.add(apiPatient)
                                }
                            }
                        } catch (exception: MongoException) {
                            throw DatabaseException(errorWhileLoadingFromDatabase)
                        }

                        respond(apiPatientList)

                    }

                }

                throws(
                    HttpStatusCode.NotFound,
                    StatusResponse(noPatientWithId("exampleId")),
                    { exception: NotFound -> StatusResponse(exception.message) }) {

                    route("{id}") {

                        /**
                         * Get a specific patient by id
                         */
                        get<PatientId, ApiPatient, JwtUser>(
                            info(
                                summary = "Get",
                                description = "Get all information about the patient."
                            ),
                            example = ApiPatient.EXAMPLE
                        ) { patientId ->

                            run {

                                val loadedPatient = try {
                                    patientPersistenceService.getPatientById(patientId.id)
                                } catch (exception: IllegalArgumentException) {
                                    throw InvalidData(malformedId(patientId.id))
                                } catch (exception: MongoException) {
                                    throw DatabaseException(errorWhileLoadingFromDatabase)
                                } ?: throw NotFound(noPatientWithId(patientId.id))

                                val apiBodyMaps = mutableListOf<ApiBodyMap>()
                                loadedPatient.bodyMaps.map { currentBodyMap ->
                                    val apiFieldValues = mutableListOf<ApiFieldValues>()
                                    currentBodyMap.fieldValues.map { currentFieldValue ->
                                        apiFieldValues.add(
                                            ApiFieldValues(
                                                id = currentFieldValue.idOfField,
                                                painIntensity = currentFieldValue.painIntensity,
                                                tensionIntensity = currentFieldValue.tensionIntensity
                                            )
                                        )
                                    }
                                    apiBodyMaps.add(ApiBodyMap(currentBodyMap.id, apiFieldValues))
                                }

                                val apiChartDataWeight = mutableListOf<ApiChartData>()
                                loadedPatient.chartDataWeight.forEach { chartData ->
                                    apiChartDataWeight.add(
                                        ApiChartData(
                                            chartData.key,
                                            chartData.value
                                        )
                                    )
                                }

                                val apiChartDataMuscle = mutableListOf<ApiChartData>()
                                loadedPatient.chartDataMuscle.forEach { chartData ->
                                    apiChartDataMuscle.add(
                                        ApiChartData(
                                            chartData.key,
                                            chartData.value
                                        )
                                    )
                                }

                                val apiAnamnese = mutableListOf<ApiAnamnese>()
                                loadedPatient.anamnese.forEach { anamnese ->
                                    val apiOrgans = mutableListOf<ApiOrgan>()
                                    val apiMuscles = mutableListOf<ApiMuscle>()
                                    anamnese.organs.forEach { organ ->
                                        apiOrgans.add(
                                            ApiOrgan(
                                                name = organ.name,
                                                status = organ.status,
                                                statusChanged = organ.statusChanged,
                                                comment = organ.comment?:"",
                                                type = organ.type,
                                            )
                                        )
                                    }
                                    anamnese.muscles.forEach { muscle ->
                                        apiMuscles.add(
                                            ApiMuscle(
                                                name = muscle.name,
                                                status = muscle.status,
                                                comment = muscle.comment,
                                                type = muscle.type,
                                                checkedStateInjury = muscle.checkedStateInjury,
                                                checkedStateSwelling = muscle.checkedStateSwelling,
                                                checkedStateHematoma = muscle.checkedStateHematoma
                                            )
                                        )
                                    }
                                    apiAnamnese.add(
                                        ApiAnamnese(
                                            anamnese.id,
                                            anamnese.date,
                                            apiOrgans,
                                            apiMuscles,
                                        )
                                    )
                                }

                                val apiPatient = ApiPatient(
                                    loadedPatient._id,
                                    loadedPatient.name,
                                    loadedPatient.weight,
                                    loadedPatient.comment,
                                    loadedPatient.sex,
                                    loadedPatient.birthDate,
                                    loadedPatient.species,
                                    loadedPatient.ownerName,
                                    loadedPatient.ownerAddress,
                                    loadedPatient.ownerPhoneNumber,
                                    loadedPatient.diagnostic,
                                    loadedPatient.diagnosticSymptoms,
                                    loadedPatient.diagnosticNotes,
                                    apiChartDataWeight,
                                    apiChartDataMuscle,
                                    apiAnamnese,
                                    apiBodyMaps,
                                )
                                respond(apiPatient)

                            }

                        }

                        /**
                         * Delete a patient
                         */
                        delete<PatientId, StatusResponse, JwtUser>(
                            info(
                                summary = "Delete",
                                description = "Delete a patient."
                            ),
                            example = StatusResponse(patientDeleted("exampleId"))
                        ) { patientId ->

                            run {

                                val loadedPatient = try {
                                    patientPersistenceService.getPatientById(patientId.id)
                                } catch (exception: IllegalArgumentException) {
                                    throw InvalidData(malformedId(patientId.id))
                                } catch (exception: MongoException) {
                                    throw DatabaseException(errorWhileLoadingFromDatabase)
                                } ?: throw NotFound(noPatientWithId(patientId.id))

                                val deleteResult = try {
                                    patientPersistenceService.deletePatientById(loadedPatient._id)
                                } catch (exception: IllegalArgumentException) {
                                    throw InvalidData(malformedId(patientId.id))
                                }

                                if (!deleteResult) {
                                    throw DatabaseException(errorWhileDeletingInDatabase)
                                }

                                respond(StatusResponse(patientDeleted(loadedPatient._id)))

                            }

                        }

                        /**
                         * Update a patient
                         */
                        patch<PatientId, StatusResponse, PatientUpdate, JwtUser>(
                            info(
                                summary = "Update",
                                description = "Update a patient."
                            ),
                            exampleRequest = PatientUpdate.EXAMPLE,
                            exampleResponse = StatusResponse(patientUpdated("exampleId"))
                        ) { patientId, patientUpdate ->

                            run {

                                val loadedPatient = try {
                                    patientPersistenceService.getPatientById(patientId.id)
                                } catch (exception: IllegalArgumentException) {
                                    throw InvalidData(malformedId(patientId.id))
                                } catch (exception: MongoException) {
                                    throw DatabaseException(errorWhileUpdatingInDatabase)
                                } ?: throw NotFound(noPatientWithId(patientId.id))

                                if (patientUpdate.name != null) {
                                    loadedPatient.name = patientUpdate.name!!
                                }
                                if (patientUpdate.weight != null) {
                                    loadedPatient.weight = patientUpdate.weight!!
                                }
                                if (patientUpdate.comments != null) {
                                    loadedPatient.comment = patientUpdate.comments!!
                                }
                                if (patientUpdate.sex != null) {
                                    loadedPatient.sex = patientUpdate.sex!!
                                }
                                if (patientUpdate.birthDate != null) {
                                    loadedPatient.birthDate = patientUpdate.birthDate!!
                                }
                                if (patientUpdate.species != null) {
                                    loadedPatient.species = patientUpdate.species!!
                                }
                                if (patientUpdate.ownerName != null) {
                                    loadedPatient.ownerName = patientUpdate.ownerName!!
                                }
                                if (patientUpdate.ownerAddress != null) {
                                    loadedPatient.ownerAddress = patientUpdate.ownerAddress!!
                                }
                                if (patientUpdate.ownerPhoneNumber != null) {
                                    loadedPatient.ownerPhoneNumber =
                                        patientUpdate.ownerPhoneNumber!!
                                }
                                if (patientUpdate.diagnostic != null) {
                                    loadedPatient.diagnostic = patientUpdate.diagnostic!!
                                }
                                if (patientUpdate.diagnosticSymptoms != null) {
                                    loadedPatient.diagnosticSymptoms =
                                        patientUpdate.diagnosticSymptoms!!
                                }
                                if (patientUpdate.diagnosticNotes != null) {
                                    loadedPatient.diagnosticNotes = patientUpdate.diagnosticNotes!!
                                }
                                val chartDataWeight = mutableListOf<ChartData>()
                                if (patientUpdate.chartDataWeight != null) {
                                    patientUpdate.chartDataWeight!!.forEach { chartData ->
                                        chartDataWeight.add(
                                            ChartData(
                                                chartData.key,
                                                chartData.value
                                            )
                                        )
                                    }
                                    loadedPatient.chartDataWeight = chartDataWeight
                                }
                                val chartDataMuscle = mutableListOf<ChartData>()
                                if (patientUpdate.chartDataMuscle != null) {
                                    patientUpdate.chartDataMuscle!!.forEach { chartData ->
                                        chartDataMuscle.add(
                                            ChartData(
                                                chartData.key,
                                                chartData.value
                                            )
                                        )
                                    }
                                    loadedPatient.chartDataMuscle = chartDataMuscle
                                }

                                val updateResult = try {
                                    patientPersistenceService.updatePatient(loadedPatient)
                                } catch (exception: IllegalArgumentException) {
                                    throw InvalidData(malformedPatientData)
                                }

                                if (!updateResult) {
                                    throw DatabaseException(errorWhileUpdatingInDatabase)
                                }

                                respond(StatusResponse(patientUpdated(loadedPatient._id)))

                            }

                        }

                        route("picture") {

                            route("only-for-documentation") {

                                /**
                                 * Add picture to a patient
                                 */
                                post<PatientId, StatusResponse, PatientPicture, JwtUser>(
                                    info(
                                        summary = "Add picture",
                                        description = "Add a picture to a specific patient."
                                    ),
                                    exampleRequest = PatientPicture.EXAMPLE,
                                    exampleResponse = StatusResponse(pictureAdded("exampleId"))
                                ) { patientId, patientPicture ->

                                    run {

                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(patientId.id)
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedId(patientId.id))
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(errorWhileLoadingFromDatabase)
                                        } ?: throw NotFound(noPatientWithId(patientId.id))

                                        val file =
                                            File("./${loadedPatient._id}-${loadedPatient.pictures.size}")
                                        withContext(Dispatchers.IO) {
                                            patientPicture.picture.use { inputStream ->
                                                file.outputStream().use { outputStream ->
                                                    inputStream.copyTo(outputStream)
                                                }
                                            }
                                        }

                                        val pictures = loadedPatient.pictures.toMutableList()
                                        pictures.add(file)
                                        loadedPatient.pictures = pictures.toList()

                                        val updateResult = try {
                                            patientPersistenceService.updatePatient(loadedPatient)
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedPatientData)
                                        }

                                        if (!updateResult) {
                                            throw DatabaseException(errorWhileUpdatingInDatabase)
                                        }

                                        respond(StatusResponse(pictureAdded(loadedPatient._id)))
                                        file.delete()
                                    }

                                }

                            }

                            route("{pictureNumber}") {

                                /**
                                 * Get a specific patient by id
                                 */
                                get<PatientIdAndPictureNumber, PatientPicture, JwtUser>(
                                    info(
                                        summary = "Get picture",
                                        description = "Get a specific picture of a specific patient."
                                    ),
                                    example = PatientPicture.EXAMPLE
                                ) { patientIdAndPictureNumber ->

                                    run {

                                        val loadedPatient = try {
                                            patientPersistenceService
                                                .getPatientById(patientIdAndPictureNumber.id)
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedId(patientIdAndPictureNumber.id))
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(errorWhileLoadingFromDatabase)
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndPictureNumber.id
                                            )
                                        )

                                        if (loadedPatient.pictures.isEmpty()) {
                                            throw NotFound(
                                                patientHasNoPictures(
                                                    loadedPatient._id
                                                )
                                            )
                                        }

                                        if (patientIdAndPictureNumber.pictureNumber >= loadedPatient.pictures.size) {
                                            throw NotFound(
                                                patientHasOnlyNPictures(
                                                    loadedPatient._id,
                                                    loadedPatient.pictures.size
                                                )
                                            )
                                        }

                                        val requestedPicture =
                                            loadedPatient.pictures[patientIdAndPictureNumber.pictureNumber]
                                                .readBytes()

                                        respond(PatientPicture(requestedPicture.inputStream()))

                                    }

                                }

                            }

                        }

                        route("anamnese") {

                            /**
                             * Add anamnese to a patient
                             */
                            post<PatientId, AnamneseCreateSuccessful, ApiAnamneseCreateUpdate, JwtUser>(
                                info(
                                    summary = "Add anamnese",
                                    description = "Add a anamnese to a specific patient."
                                ),
                                exampleRequest = ApiAnamneseCreateUpdate.EXAMPLE,
                                exampleResponse = AnamneseCreateSuccessful.EXAMPLE
                            )
                            { patientId, anamneseCreate ->

                                run {

                                    val loadedPatient = try {
                                        patientPersistenceService.getPatientById(patientId.id)
                                    } catch (exception: IllegalArgumentException) {
                                        throw InvalidData(malformedId(patientId.id))
                                    } catch (exception: MongoException) {
                                        throw DatabaseException(errorWhileLoadingFromDatabase)
                                    } ?: throw NotFound(noPatientWithId(patientId.id))

                                    val anamneseId = if (loadedPatient.anamnese.isEmpty()) {
                                        minAnamneseId
                                    } else {
                                        (loadedPatient.anamnese.maxOf { it.id.toInt() } + 1)
                                    }


                                    val organs = mutableListOf<Organ>()
                                    val muscles = mutableListOf<Muscle>()

                                    val anamneseList = loadedPatient.anamnese.toMutableList()


                                    anamneseCreate.muscles.forEach { apiMuscle ->
                                        muscles.add(
                                            Muscle(
                                                name = apiMuscle.name,
                                                status = apiMuscle.status,
                                                comment = apiMuscle.comment,
                                                type = apiMuscle.type,
                                                checkedStateInjury = apiMuscle.checkedStateInjury,
                                                checkedStateSwelling = apiMuscle.checkedStateSwelling,
                                                checkedStateHematoma = apiMuscle.checkedStateHematoma,
                                            )
                                        )
                                    }
                                    anamneseCreate.organs.forEach { apiOrgan ->
                                        organs.add(
                                            Organ(
                                                name = apiOrgan.name,
                                                status = apiOrgan.status,
                                                statusChanged = apiOrgan.statusChanged,
                                                comment = apiOrgan.comment,
                                                type = apiOrgan.type,
                                            )
                                        )

                                    }
                                    anamneseList.add(
                                        Anamnese(
                                            anamneseId.toString(),
                                            anamneseCreate.date,
                                            organs.toList(),
                                            muscles.toList(),
                                        )
                                    )
                                    loadedPatient.anamnese = anamneseList.toList()

                                    val updateResult = try {
                                        patientPersistenceService.updatePatient(loadedPatient)
                                    } catch (exception: IllegalArgumentException) {
                                        throw InvalidData(malformedPatientData)
                                    }

                                    if (!updateResult) {
                                        throw DatabaseException(errorWhileUpdatingInDatabase)
                                    }

                                    respond(AnamneseCreateSuccessful(anamneseId.toString()))

                                }

                            }


                            route("{anamneseId}") {

                                /**
                                 * Get a anamnese
                                 */
                                get<PatientIdAndAnamneseId, ApiAnamnese, JwtUser>(
                                    info(
                                        summary = "Get",
                                        description = "Get a anamnese by the anamnese id."
                                    ),
                                    example = ApiAnamnese.EXAMPLE
                                ) { patientIdAndAnamneseId ->
                                    run {

                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(
                                                patientIdAndAnamneseId.id
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedId(patientIdAndAnamneseId.id))
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(
                                                errorWhileLoadingFromDatabase
                                            )
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndAnamneseId.id
                                            )
                                        )

                                        val foundAnamnese =
                                            loadedPatient.anamnese.find { anamnese -> patientIdAndAnamneseId.anamneseId == anamnese.id }
                                                ?: throw NotFound(
                                                    anamneseIdNotFound(
                                                        patientIdAndAnamneseId.anamneseId
                                                    )
                                                )

                                        val apiOrgans = mutableListOf<ApiOrgan>()
                                        val apiMuscles = mutableListOf<ApiMuscle>()
                                        foundAnamnese.organs.forEach { organ ->
                                            apiOrgans.add(
                                                ApiOrgan(
                                                    name = organ.name,
                                                    status = organ.status,
                                                    statusChanged = organ.statusChanged,
                                                    comment = organ.comment?:"",
                                                    type = organ.type,
                                                )
                                            )
                                        }
                                        foundAnamnese.muscles.forEach { muscle ->
                                            apiMuscles.add(
                                                ApiMuscle(
                                                    name = muscle.name,
                                                    status = muscle.status,
                                                    comment = muscle.comment,
                                                    type = muscle.type,
                                                    checkedStateInjury = muscle.checkedStateInjury,
                                                    checkedStateSwelling = muscle.checkedStateSwelling,
                                                    checkedStateHematoma = muscle.checkedStateHematoma
                                                )
                                            )
                                        }

                                        respond(
                                            ApiAnamnese(
                                                foundAnamnese.id,
                                                foundAnamnese.date,
                                                apiOrgans,
                                                apiMuscles,
                                            )
                                        )

                                    }

                                }

                                /**
                                 * Update a anamnese
                                 */
                                patch<PatientIdAndAnamneseId, StatusResponse, ApiAnamneseCreateUpdate, JwtUser>(
                                    info(
                                        summary = "Update",
                                        description = "Update a anamnese by the anamnese id."
                                    ),
                                    exampleRequest = ApiAnamneseCreateUpdate.EXAMPLE,
                                    exampleResponse = StatusResponse(anamneseUpdated("exampleId"))
                                ) { patientIdAndAnamneseId, apiAnamneseUpdate ->
                                    run {

                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(
                                                patientIdAndAnamneseId.id
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedId(patientIdAndAnamneseId.id))
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(
                                                errorWhileLoadingFromDatabase
                                            )
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndAnamneseId.id
                                            )
                                        )

                                        val foundAnamnese =
                                            loadedPatient.anamnese.find { anamnese -> patientIdAndAnamneseId.anamneseId == anamnese.id }
                                                ?: throw NotFound(
                                                    anamneseIdNotFound(
                                                        patientIdAndAnamneseId.anamneseId
                                                    )
                                                )

                                        val newAnamneseList =
                                            loadedPatient.anamnese.toMutableList()
                                        if (!newAnamneseList.remove(foundAnamnese)) {
                                            throw NotFound(
                                                anamneseIdNotFound(
                                                    patientIdAndAnamneseId.anamneseId
                                                )
                                            )
                                        }
                                        val organs = mutableListOf<Organ>()
                                        val muscles = mutableListOf<Muscle>()

                                        apiAnamneseUpdate.muscles.forEach { apiMuscle ->
                                            muscles.add(
                                                Muscle(
                                                    name = apiMuscle.name,
                                                    status = apiMuscle.status,
                                                    comment = apiMuscle.comment,
                                                    type = apiMuscle.type,
                                                    checkedStateInjury = apiMuscle.checkedStateInjury,
                                                    checkedStateSwelling = apiMuscle.checkedStateSwelling,
                                                    checkedStateHematoma = apiMuscle.checkedStateHematoma,
                                                )
                                            )
                                        }
                                        apiAnamneseUpdate.organs.forEach { apiOrgan ->
                                            organs.add(
                                                Organ(
                                                    name = apiOrgan.name,
                                                    status = apiOrgan.status,
                                                    statusChanged = apiOrgan.statusChanged,
                                                    comment = apiOrgan.comment,
                                                    type = apiOrgan.type,
                                                )
                                            )

                                        }
                                        newAnamneseList.add(
                                            Anamnese(
                                                foundAnamnese.id,
                                                apiAnamneseUpdate.date,
                                                organs,
                                                muscles,
                                            )
                                        )
                                        loadedPatient.anamnese = newAnamneseList

                                        val updateResult = try {
                                            patientPersistenceService.updatePatient(
                                                loadedPatient
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedPatientData)
                                        }

                                        if (!updateResult) {
                                            throw DatabaseException(
                                                errorWhileUpdatingInDatabase
                                            )
                                        }

                                        respond(
                                            StatusResponse(
                                                anamneseUpdated(
                                                    foundAnamnese.id
                                                )
                                            )
                                        )

                                    }

                                }

                                /**
                                 * Delete a anamnese
                                 */
                                delete<PatientIdAndAnamneseId, StatusResponse, JwtUser>(
                                    info(
                                        summary = "Delete",
                                        description = "Delete a anamnese by the anamnese id."
                                    ),
                                    example = StatusResponse(anamneseDeleted("exampleId"))
                                ) { patientIdAndAnamneseId ->
                                    run {

                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(
                                                patientIdAndAnamneseId.id
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(
                                                malformedId(
                                                    patientIdAndAnamneseId.id
                                                )
                                            )
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(
                                                errorWhileLoadingFromDatabase
                                            )
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndAnamneseId.id
                                            )
                                        )

                                        val foundAnamnese =
                                            loadedPatient.anamnese.find { anamnese -> patientIdAndAnamneseId.anamneseId == anamnese.id }
                                                ?: throw NotFound(
                                                    anamneseIdNotFound(
                                                        patientIdAndAnamneseId.anamneseId
                                                    )
                                                )

                                        val newAnamneseList =
                                            loadedPatient.anamnese.toMutableList()
                                        if (!newAnamneseList.remove(foundAnamnese)) {
                                            throw NotFound(
                                                anamneseIdNotFound(
                                                    patientIdAndAnamneseId.anamneseId
                                                )
                                            )
                                        }
                                        loadedPatient.anamnese = newAnamneseList.toList()

                                        val deleteResult = try {
                                            patientPersistenceService.updatePatient(
                                                loadedPatient
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(
                                                malformedId(
                                                    patientIdAndAnamneseId.id
                                                )
                                            )
                                        }

                                        if (!deleteResult) {
                                            throw DatabaseException(
                                                errorWhileDeletingInDatabase
                                            )
                                        }

                                        respond(StatusResponse(anamneseDeleted(foundAnamnese.id)))

                                    }

                                }

                            }

                        }

                        route("/bodymap") {

                            /**
                             * Get all bodymaps by id of the patient
                             */
                            get<PatientId, List<ApiBodyMap>, JwtUser>(
                                info(
                                    summary = "Get",
                                    description = "Get all bodyMaps of the patient."
                                ),
                                example = ApiBodyMap.EXAMPLE_LIST
                            ) { patientId ->
                                run {
                                    val loadedPatient = try {
                                        patientPersistenceService.getPatientById(patientId.id)
                                    } catch (exception: IllegalArgumentException) {
                                        throw InvalidData(malformedId(patientId.id))
                                    } catch (exception: MongoException) {
                                        throw DatabaseException(
                                            errorWhileLoadingFromDatabase
                                        )
                                    } ?: throw NotFound(noPatientWithId(patientId.id))

                                    val apiBodyMaps = mutableListOf<ApiBodyMap>()
                                    loadedPatient.bodyMaps.map { outerIt ->
                                        val apiFieldValues = mutableListOf<ApiFieldValues>()
                                        outerIt.fieldValues.map { innerIt ->
                                            apiFieldValues.add(
                                                ApiFieldValues(
                                                    id = innerIt.idOfField,
                                                    painIntensity = innerIt.painIntensity,
                                                    tensionIntensity = innerIt.tensionIntensity
                                                )
                                            )
                                        }
                                        apiBodyMaps.add(
                                            ApiBodyMap(
                                                outerIt.id,
                                                apiFieldValues
                                            )
                                        )
                                    }

                                    respond(apiBodyMaps)
                                }

                            }

                            /**
                             * Create a new bodymap
                             */
                            post<PatientId, BodyMapCreateSuccessful, ApiBodyMapCreateUpdate, JwtUser>(
                                info(
                                    summary = "Create bodymap",
                                    description = "Creates a new bodymap with the given parameters."
                                ),
                                exampleRequest = ApiBodyMapCreateUpdate.EXAMPLE,
                                exampleResponse = BodyMapCreateSuccessful.EXAMPLE

                            ) { patientId, bodyMapCreate ->

                                run {
                                    val loadedPatient = try {
                                        patientPersistenceService.getPatientById(
                                            patientId.id
                                        )
                                    } catch (exception: IllegalArgumentException) {
                                        throw InvalidData(malformedId(patientId.id))
                                    } catch (exception: MongoException) {
                                        throw DatabaseException(
                                            errorWhileLoadingFromDatabase
                                        )
                                    } ?: throw NotFound(noPatientWithId(patientId.id))

                                    val fieldValues = mutableListOf<FieldValue>()
                                    bodyMapCreate.bodyMapFieldValues.forEach { apiFieldValues ->
                                        fieldValues.add(
                                            FieldValue(
                                                idOfField = apiFieldValues.id,
                                                painIntensity = apiFieldValues.painIntensity,
                                                tensionIntensity = apiFieldValues.tensionIntensity
                                            )
                                        )
                                    }

                                    val idOfBodyMap =
                                        if (loadedPatient.bodyMaps.isEmpty()) {
                                            minBodyMapId
                                        } else {
                                            (loadedPatient.bodyMaps.maxOf { it.id.toInt() } + 1)
                                        }
                                    val patientBodyMap = BodyMap(
                                        idOfBodyMap.toString(), fieldValues
                                    )
                                    val allPatientBodyMaps =
                                        loadedPatient.bodyMaps.plus(patientBodyMap)
                                    loadedPatient.bodyMaps = allPatientBodyMaps
                                    val updateResult = try {
                                        patientPersistenceService.updatePatient(
                                            loadedPatient
                                        )
                                    } catch (exception: IllegalArgumentException) {
                                        throw InvalidData(malformedPatientData)
                                    }
                                    if (!updateResult) {
                                        throw DatabaseException(errorWhileUpdatingInDatabase)
                                    }
                                    respond(BodyMapCreateSuccessful(idOfBodyMap.toString()))
                                }

                            }

                            route("{bodymapid}") {

                                /**
                                 * Get the bodymap by id of the bodymap
                                 */
                                get<PatientIdAndBodyMapId, ApiBodyMap, JwtUser>(
                                    info(
                                        summary = "Get",
                                        description = "Get the bodymap with the bodymapid."
                                    ),
                                    example = ApiBodyMap.EXAMPLE
                                ) { patientIdAndBodyMapId ->
                                    run {
                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(
                                                patientIdAndBodyMapId.id
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(
                                                malformedId(
                                                    patientIdAndBodyMapId.id
                                                )
                                            )
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(
                                                errorWhileLoadingFromDatabase
                                            )
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndBodyMapId.id
                                            )
                                        )
                                        val foundBodyMap =
                                            loadedPatient.bodyMaps.find { bodyMap -> patientIdAndBodyMapId.bodymapid == bodyMap.id }
                                                ?: throw InvalidData(
                                                    malformedOrNotFoundIdOfBodyMap(
                                                        patientIdAndBodyMapId.bodymapid
                                                    )
                                                )

                                        val apiFieldValues = mutableListOf<ApiFieldValues>()
                                        foundBodyMap.fieldValues.map { fieldValue ->
                                            apiFieldValues.add(
                                                ApiFieldValues(
                                                    id = fieldValue.idOfField,
                                                    painIntensity = fieldValue.painIntensity,
                                                    tensionIntensity = fieldValue.tensionIntensity
                                                )
                                            )
                                        }
                                        val apiBodyMap =
                                            ApiBodyMap(foundBodyMap.id, apiFieldValues)

                                        respond(apiBodyMap)
                                    }

                                }

                                /**
                                 * Update a bodyMap
                                 */
                                patch<PatientIdAndBodyMapId, StatusResponse, ApiBodyMapCreateUpdate, JwtUser>(
                                    info(
                                        summary = "Update",
                                        description = "Update a bodyMap."
                                    ),
                                    exampleRequest = ApiBodyMapCreateUpdate.EXAMPLE,
                                    exampleResponse = StatusResponse(bodyMapUpdated("exampleId"))
                                ) { patientIdAndBodyMapId, apiBodyMap ->

                                    run {
                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(
                                                patientIdAndBodyMapId.id
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(
                                                malformedId(
                                                    patientIdAndBodyMapId.id
                                                )
                                            )
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(
                                                errorWhileUpdatingInDatabase
                                            )
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndBodyMapId.id
                                            )
                                        )
                                        val foundBodyMap =
                                            loadedPatient.bodyMaps.find { bodyMap -> patientIdAndBodyMapId.bodymapid == bodyMap.id }
                                                ?: throw InvalidData(
                                                    malformedOrNotFoundIdOfBodyMap(
                                                        patientIdAndBodyMapId.bodymapid
                                                    )
                                                )
                                        val newBodyMapList =
                                            loadedPatient.bodyMaps.toMutableList()
                                        if (!newBodyMapList.remove(foundBodyMap)) {
                                            throw InvalidData(
                                                malformedOrNotFoundIdOfBodyMap(
                                                    patientIdAndBodyMapId.bodymapid
                                                )
                                            )
                                        }
                                        val fieldValues = mutableListOf<FieldValue>()
                                        apiBodyMap.bodyMapFieldValues.forEach { apiFieldValues ->
                                            fieldValues.add(
                                                FieldValue(
                                                    idOfField = apiFieldValues.id,
                                                    painIntensity = apiFieldValues.painIntensity,
                                                    tensionIntensity = apiFieldValues.tensionIntensity
                                                )
                                            )
                                        }

                                        val allPatientBodyMaps = newBodyMapList.plus(
                                            BodyMap(
                                                patientIdAndBodyMapId.bodymapid, fieldValues
                                            )
                                        )
                                        loadedPatient.bodyMaps = allPatientBodyMaps

                                        val updateResult = try {
                                            patientPersistenceService.updatePatient(
                                                loadedPatient
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(malformedPatientData)
                                        }

                                        if (!updateResult) {
                                            throw DatabaseException(
                                                errorWhileUpdatingInDatabase
                                            )
                                        }

                                        respond(
                                            StatusResponse(
                                                bodyMapUpdated(
                                                    patientIdAndBodyMapId.bodymapid
                                                )
                                            )
                                        )

                                    }

                                }

                                /**
                                 * Delete a bodyMap
                                 */
                                delete<PatientIdAndBodyMapId, StatusResponse, JwtUser>(
                                    info(
                                        summary = "Delete",
                                        description = "Delete a bodyMap."
                                    ),
                                    example = StatusResponse(bodyMapDeleted("exampleId"))
                                ) { patientIdAndBodyMapId ->
                                    run {

                                        val loadedPatient = try {
                                            patientPersistenceService.getPatientById(
                                                patientIdAndBodyMapId.id
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(
                                                malformedId(
                                                    patientIdAndBodyMapId.id
                                                )
                                            )
                                        } catch (exception: MongoException) {
                                            throw DatabaseException(
                                                errorWhileLoadingFromDatabase
                                            )
                                        } ?: throw NotFound(
                                            noPatientWithId(
                                                patientIdAndBodyMapId.id
                                            )
                                        )
                                        val foundBodyMap =
                                            loadedPatient.bodyMaps.find { bodyMap -> patientIdAndBodyMapId.bodymapid == bodyMap.id }
                                                ?: throw InvalidData(
                                                    malformedOrNotFoundIdOfBodyMap(
                                                        patientIdAndBodyMapId.bodymapid
                                                    )
                                                )

                                        val newBodyMapList =
                                            loadedPatient.bodyMaps.toMutableList()
                                        if (!newBodyMapList.remove(foundBodyMap)) {
                                            throw InvalidData(
                                                malformedOrNotFoundIdOfBodyMap(
                                                    patientIdAndBodyMapId.bodymapid
                                                )
                                            )
                                        }
                                        loadedPatient.bodyMaps = newBodyMapList.toList()
                                        val deleteResult = try {
                                            patientPersistenceService.updatePatient(
                                                loadedPatient
                                            )
                                        } catch (exception: IllegalArgumentException) {
                                            throw InvalidData(
                                                malformedId(
                                                    patientIdAndBodyMapId.bodymapid
                                                )
                                            )
                                        }

                                        if (!deleteResult) {
                                            throw DatabaseException(
                                                errorWhileDeletingInDatabase
                                            )
                                        }

                                        respond(StatusResponse(bodyMapDeleted(foundBodyMap.id)))

                                    }

                                }

                            }

                        }

                    }

                }

            }

        }

    }

}

