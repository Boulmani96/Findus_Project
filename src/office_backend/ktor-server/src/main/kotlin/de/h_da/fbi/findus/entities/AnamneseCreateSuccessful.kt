package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response

@Response("Response contains the id of the created anamnese.", statusCode = 201)
data class AnamneseCreateSuccessful(
    var id: String,
) {
    companion object {
        val EXAMPLE = AnamneseCreateSuccessful("anamneseId")
    }
}
