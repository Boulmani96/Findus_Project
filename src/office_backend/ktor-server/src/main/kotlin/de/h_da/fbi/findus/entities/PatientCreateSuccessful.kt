package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response
import kotlinx.serialization.Serializable

@Response("Response contains the id of the created patient.", statusCode = 201)
@Serializable
data class PatientCreateSuccessful(
    var id: String
) {
    companion object {
        val EXAMPLE = PatientCreateSuccessful("patientId")
    }
}
