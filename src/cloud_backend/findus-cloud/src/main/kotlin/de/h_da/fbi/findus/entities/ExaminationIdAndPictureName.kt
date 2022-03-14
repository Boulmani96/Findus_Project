package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.parameters.PathParam
import kotlinx.serialization.Serializable

@Serializable
data class ExaminationIdAndPictureName(
    @PathParam("The id of the medical examination")
    val id: String,
    @PathParam("The name of the medical examination picture")
    val pictureName: String
)
