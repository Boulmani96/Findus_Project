package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.parameters.PathParam
import kotlinx.serialization.Serializable

@Serializable
data class PatientIdAndPictureNumber(
    @PathParam("The id of the patient.")
    val id: String,
    @PathParam("The number of the patient picture.")
    val pictureNumber: Int
)
