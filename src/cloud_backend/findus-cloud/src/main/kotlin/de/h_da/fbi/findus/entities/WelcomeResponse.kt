package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

/**
 * Welcome response holds a message that is sent from the API when the welcome route is called
 *
 * @property responseText the welcome text
 * @constructor Creates a valid Welcome response
 */
@Request("Welcome request body.")
@Serializable
data class WelcomeResponse(
    @Required
    val responseText: String,
) {
    companion object {
        val EXAMPLE = WelcomeResponse("hello from the api")
    }
}
