package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.parameters.PathParam
import kotlinx.serialization.Serializable

@Serializable
data class PatientId(
    @PathParam("The id of the patient.")
    val id: String
)
