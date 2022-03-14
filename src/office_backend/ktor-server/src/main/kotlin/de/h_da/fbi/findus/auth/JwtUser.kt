package de.h_da.fbi.findus.auth

import com.papsign.ktor.openapigen.annotations.Response
import io.ktor.auth.*
import kotlinx.serialization.Serializable

/**
 * Data class, that contains information of an authenticated user that is authenticated via jwt
 */
@Response("The jwt user who is authenticated in the API.", statusCode = 200)
@Serializable
data class JwtUser(
    val id: Int,
    val name: String
) : Principal {
    companion object {
        val EXAMPLE = JwtUser(1, "Admin")
    }
}
