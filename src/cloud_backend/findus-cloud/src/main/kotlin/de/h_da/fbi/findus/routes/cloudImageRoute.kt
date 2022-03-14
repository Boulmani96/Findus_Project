package de.h_da.fbi.findus.routes

import de.h_da.fbi.findus.database.services.CloudService
import de.h_da.fbi.findus.entities.StatusResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import java.io.File
import java.security.InvalidParameterException

const val pictureRoute = ""
const val pictureAdded = "Picture was added to medical examination {id} successful!"
const val noCheckUpWithId = "No medical examinations with id {id}!"
const val missingOrMalformedId = "Missing or malformed id!"

fun Route.cloudImageRoute() {
    val patientPersistenceService = CloudService()
    val logger = KotlinLogging.logger {}
    post(pictureRoute) {
        try {
            val id = call.parameters["id"]
                ?: return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    StatusResponse(missingOrMalformedId)
                )
            val nameOfPicture = call.parameters["pictureName"]
                ?: return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    StatusResponse(missingOrMalformedId)
                )
            val loadedExamination = try {
                patientPersistenceService.getExaminationById(id)
            } catch (exception: InvalidParameterException) {
                logger.error(exception) { "could not load medical examination Error: $exception" }
                null
            }
            loadedExamination ?: return@post call.respond(
                status = HttpStatusCode.NotFound,
                StatusResponse(noCheckUpWithId.replace("{id}", id))
            )
            val file =
                File("./$nameOfPicture")
            file.deleteOnExit()
            withContext(Dispatchers.IO) {
                call.receiveStream().use { inputStream ->
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }

            val pictures = loadedExamination.pictures.toMutableList()
            pictures.add(file)
            loadedExamination.pictures =
                pictures.toList()

            try {
                if (!patientPersistenceService.uploadExaminationImages(loadedExamination)) {
                    return@post call.respond(
                        status = HttpStatusCode.InternalServerError,
                        StatusResponse(errorWhileUpdatingInDatabase)
                    )
                }
            } catch (exception: IllegalArgumentException) {
                logger.error(exception) { "could not save image Error: $exception" }
                return@post call.respond(
                    status = HttpStatusCode.InternalServerError,
                    StatusResponse(errorWhileUpdatingInDatabase)
                )
            }

            return@post call.respond(
                status = HttpStatusCode.OK,
                StatusResponse(pictureAdded.replace("{id}", loadedExamination._id))
            )

        } catch (exception: Exception) {
            logger.error(exception) { "could not save image Error: $exception" }
            return@post call.respond(
                status = HttpStatusCode.BadRequest,
                StatusResponse(invalidData)
            )
        }
    }
}