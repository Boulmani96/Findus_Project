package de.h_da.fbi.findus.routes

import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.auth.OpenAPIAuthenticatedRoute
import com.papsign.ktor.openapigen.route.path.auth.get
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.post
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import com.papsign.ktor.openapigen.route.throws
import de.h_da.fbi.findus.auth.JwtConfig
import de.h_da.fbi.findus.auth.JwtUser
import de.h_da.fbi.findus.entities.LoginRequest
import de.h_da.fbi.findus.entities.LoginResponse
import de.h_da.fbi.findus.entities.UserRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*

// Its a PROTOTYPE todo: users should not be hardcoded!!! Maybe load from database?
val userRepository = UserRepository()

const val loginSuccessful = "Login successful!"
const val credentialsInvalid = "Credentials are invalid!"

class InvalidCredentials : Exception()

fun NormalOpenAPIRoute.loginRoute(jwtConfig: JwtConfig) {

    route("login") {

        throws(
            HttpStatusCode.NotFound,
            LoginResponse(false, null, credentialsInvalid),
            { _: InvalidCredentials -> LoginResponse(false, null, credentialsInvalid) }) {

            post<Unit, LoginResponse, LoginRequest>(
                info(
                    summary = "Login",
                    description = "Login to the Findus Office backend API.",
                ),
                exampleRequest = LoginRequest.EXAMPLE,
                exampleResponse = LoginResponse.EXAMPLE,
            ) { _, loginRequest ->

                run {

                    val user = userRepository.getUser(loginRequest.username, loginRequest.password)
                    if (user == null) {
                        throw InvalidCredentials()
                    } else {
                        val token = jwtConfig.generateToken(JwtUser(user.id, user.name))
                        respond(LoginResponse(true, token, loginSuccessful))
                    }

                }

            }

        }

    }

}

fun OpenAPIAuthenticatedRoute<JwtUser>.meRoute() {

    route("me") {


        get<Unit, JwtUser, JwtUser>(
            info(
                summary = "Me",
                description = "Return the user associated to the jwt token.",
            ),
            example = JwtUser.EXAMPLE
        ) {

            run {

                val user = pipeline.call.authentication.principal as JwtUser
                respond(user)

            }

        }

    }

}
