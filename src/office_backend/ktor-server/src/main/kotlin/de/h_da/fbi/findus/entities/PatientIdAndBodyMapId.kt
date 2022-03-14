package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.parameters.PathParam

data class PatientIdAndBodyMapId(
    @PathParam("The id of the patient.")
    val id: String,
    @PathParam("The id of the bodymap.")
    val bodymapid: String
)
