package de.h_da.fbi.findus.entities

import kotlinx.serialization.Serializable

/**
 * Diagnose input holds the id of a patient and a description of the animal's symptoms
 *
 * @property id the id of the patient
 * @property description the diagnosed symptoms
 * @constructor Creates a valid Diagnose input
 */
@Serializable
data class DiagnoseInput(
    val id: String,
    val description: String,
) {
    companion object {
        val EXAMPLE = DiagnoseInput("123456", "has a broken leg")
    }
}