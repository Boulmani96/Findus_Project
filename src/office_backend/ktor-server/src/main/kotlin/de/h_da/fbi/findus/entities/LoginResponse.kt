package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response
import kotlinx.serialization.Serializable

@Response("Summary of the login.", statusCode = 200)
@Serializable
data class LoginResponse(
    var ok: Boolean,
    var token: String?,
    val message: String
) {
    companion object {
        val EXAMPLE = LoginResponse(true, "yourToken", "Login successful!")
    }
}
