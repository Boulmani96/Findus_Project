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
import de.h_da.fbi.findus.auth.JwtConfig
import de.h_da.fbi.findus.auth.JwtProvider
import de.h_da.fbi.findus.auth.auth
import de.h_da.fbi.findus.database.di.databaseModule
import de.h_da.fbi.findus.entities.StatusResponse
import de.h_da.fbi.findus.routes.*
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KType

// Its a PROTOTYPE todo: secret should not be hardcoded!!! Maybe load from file?
const val jwtSecret: String = "secret"

val logger: Logger = LoggerFactory.getLogger("Main")
val authProvider = JwtProvider()

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(isTesting: Boolean = false) {

    try {
        val isSet = if (isTesting) {
            dotenv().get("MONGO_URI_TEST")
        } else {
            dotenv().get("MONGO_URI")
        }

        if (isSet == "") {
            throw IllegalArgumentException()
        }
    } catch (exception: IllegalArgumentException) {
        logger.error("Error while connecting to the database: " + exception.message)
        return
    }

    install(Koin) {
        modules(databaseModule(isTesting))
    }

    install(OpenAPIGen) {
        info {
            version = "0.0.1"
            title = "Findus API"
            description = "The Findus API for the Office backend."
            contact {
                name = "Hochschule Darmstadt PSE WS21/22"
                url = "https://code.fbi.h-da.de/pse-trapp/findus"
            }
        }

        server("http://localhost:8082/") {
            description = "Local server"
        }

        addModules(authProvider)

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

    val jwtConfig = JwtConfig(jwtSecret)
    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }

    routing {

        get("/") {
            call.respondRedirect("/swagger-ui/index.html?url=/openapi.json", true)
        }

        get("/openapi.json") {
            call.respond(GsonBuilder().create().toJson(application.openAPIGen.api.serialize()))
        }

    }

    apiRouting {

        throws(
            HttpStatusCode.BadRequest,
            StatusResponse(invalidData),
            { _: Exception -> StatusResponse(invalidData) }) {

            route("/api") {

                tag(Tags.LOGIN) {
                    loginRoute(jwtConfig)
                }

                auth {

                    tag(Tags.ME) {
                        meRoute()
                    }

                    tag(Tags.PATIENT) {
                        patientRoute()
                    }

                }
                tag(Tags.DUMMY) {
                    dummyRoute()
                }

            }

        }

    }

    routing {

        authenticate {

            tempUploadPicture()

        }

    }

}
