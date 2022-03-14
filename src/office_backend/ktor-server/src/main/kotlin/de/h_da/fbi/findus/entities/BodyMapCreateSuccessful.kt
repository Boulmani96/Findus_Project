package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response

@Response("Response contains the id of the created bodymap.", statusCode = 201)
data class BodyMapCreateSuccessful(
    var id: String
) {
    companion object {
        val EXAMPLE = BodyMapCreateSuccessful("bodyMapId")
    }
}
