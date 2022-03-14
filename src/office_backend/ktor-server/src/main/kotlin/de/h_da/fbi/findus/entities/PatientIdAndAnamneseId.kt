package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.parameters.PathParam

data class PatientIdAndAnamneseId(
    @PathParam("The id of the patient.")
    val id: String,
    @PathParam("The id of the anamnese.")
    val anamneseId: String,
)
