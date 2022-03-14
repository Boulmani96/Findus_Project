package de.h_da.fbi.findus

import com.google.gson.GsonBuilder
import com.papsign.ktor.openapigen.OpenAPIGen
import com.papsign.ktor.openapigen.exceptions.OpenAPIBadContentException
import com.papsign.ktor.openapigen.exceptions.OpenAPIRequiredFieldException
import com.papsign.ktor.openapigen.openAPIGen
import com.papsign.ktor.openapigen.route.apiRouting
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.tag
import com.papsign.ktor.openapigen.route.throws
import com.papsign.ktor.openapigen.schema.namer.DefaultSchemaNamer
import com.papsign.ktor.openapigen.schema.namer.SchemaNamer
import de.h_da.fbi.findus.database.di.cloudDatabaseModule
import de.h_da.fbi.findus.entities.StatusResponse
import de.h_da.fbi.findus.routes.Tags
import de.h_da.fbi.findus.routes.cloudImageRoute
import de.h_da.fbi.findus.routes.cloudRoute
import de.h_da.fbi.findus.routes.invalidData
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KType


val logger: Logger = LoggerFactory.getLogger("Main")
const val mongoURI = "MONGO_URI"
const val apiRoute = "/api"
const val illegalUriMessage = "mongoURI is invalid"
const val uploadPictureRoute = "$apiRoute/examination/upload/picture/{id}/name/{pictureName}"
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    try {
        val mongoURI = dotenv().get(mongoURI)
        if (mongoURI == "") {
            throw IllegalArgumentException(illegalUriMessage)
        }
    } catch (exception: IllegalArgumentException) {
        logger.error("Error while connecting to the database: " + exception.message)
        return
    }
    install(Koin) {
        modules(cloudDatabaseModule)
    }
    install(OpenAPIGen) {
        info {
            version = "0.0.1"
            title = "Findus Cloud API"
            description = "The Findus Cloud API for the cloud backend."
            contact {
                name = "Hochschule Darmstadt PSE WS21/22"
                url = "https://code.fbi.h-da.de/pse-trapp/findus"
            }
        }

        server("http://localhost:8084/") {
            description = "Local server"
        }

        replaceModule(DefaultSchemaNamer, object : SchemaNamer {
            val regex = Regex("[A-Za-z0-9_.]+")
            override fun get(type: KType): String {
                return type.toString().replace(regex) { it.value.split(".").last() }
                    .replace(Regex(">|<|, "), "_")
            }
        })
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(StatusPages) {

        exception<OpenAPIBadContentException> { e ->
            call.respond(HttpStatusCode.NotFound, e.localizedMessage)
        }

        exception<OpenAPIRequiredFieldException> { e ->
            call.respond(HttpStatusCode.BadRequest, e.localizedMessage)
        }

    }
    routing {

        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }

        get("/openapi.json") {
            call.respond(GsonBuilder().create().toJson(application.openAPIGen.api.serialize()))
        }
        route(uploadPictureRoute) {
            cloudImageRoute()
        }
    }
    apiRouting {

        throws(
            HttpStatusCode.BadRequest,
            StatusResponse(invalidData),
            { _: Exception -> StatusResponse(invalidData) }) {

            route(apiRoute) {
                tag(Tags.EXAMINATION) {
                    cloudRoute()
                }
            }
        }

    }
}
