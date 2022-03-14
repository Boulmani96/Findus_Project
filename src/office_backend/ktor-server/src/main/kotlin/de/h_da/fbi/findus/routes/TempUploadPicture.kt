package de.h_da.fbi.findus.routes

import com.mongodb.MongoException
import de.h_da.fbi.findus.database.services.PatientService
import de.h_da.fbi.findus.entities.StatusResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

const val missingId = "Missing id!"

/**
 * This is only temporary because file uploading with the
 * framework https://github.com/papsign/Ktor-OpenAPI-Generator
 * does currently not work.
 * Bug is already reported: https://github.com/papsign/Ktor-OpenAPI-Generator/issues/122
 */
fun Route.tempUploadPicture() {

    val patientPersistenceService = PatientService()

    post("/api/patient/{id}/picture") {
        try {

            val id = call.parameters["id"]
                ?: return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    StatusResponse(missingId)
                )

            val loadedPatient = try {
                patientPersistenceService.getPatientById(id)
            } catch (exception: IllegalArgumentException) {
                return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    StatusResponse(malformedId(id))
                )
            } catch (exception: MongoException) {
                return@post call.respond(
                    status = HttpStatusCode.InternalServerError,
                    StatusResponse(errorWhileLoadingFromDatabase)
                )
            } ?: return@post call.respond(
                status = HttpStatusCode.NotFound,
                StatusResponse(noPatientWithId(id))
            )

            val file = File("./${loadedPatient._id}-${loadedPatient.pictures.size}")
            withContext(Dispatchers.IO) {
                call.receiveStream().use { inputStream ->
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
                return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    StatusResponse(malformedPatientData)
                )
            }

            if (!updateResult) {
                return@post call.respond(
                    status = HttpStatusCode.InternalServerError,
                    StatusResponse(errorWhileUpdatingInDatabase)
                )
            }

            call.respond(StatusResponse(pictureAdded(loadedPatient._id)))
            file.delete()

        } catch (e: Exception) {
            return@post call.respond(
                status = HttpStatusCode.BadRequest,
                StatusResponse(invalidData)
            )
        }

    }

}
