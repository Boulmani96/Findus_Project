package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response
import kotlinx.serialization.Serializable

/**
 * Medical examination successful holds the response that is sent from the API when an examination object got created
 *
 * @property id the id of the newly created examination object
 * @constructor Creates a valid Medical examination successful object
 */
@Response("Response contains the id of the created patient.", statusCode = 201)
@Serializable
data class MedicalExaminationSuccessful(
    var id: String
) {
    companion object {
        val EXAMPLE = MedicalExaminationSuccessful("examinationId")
    }
}
