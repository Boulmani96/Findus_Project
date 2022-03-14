package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Request("Login request body.")
@Serializable
data class LoginRequest(
    @Required
    val username: String,
    @Required
    val password: String,
) {
    companion object {
        val EXAMPLE = LoginRequest("Admin", "Admin")
    }
}
