package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import de.h_da.fbi.findus.database.models.Sex
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Request("A request to create a patient.")
@Serializable
data class PatientCreate(
    @Required
    var name: String,
    @Required
    var weight: Float,
    @Required
    var comments: String,
    @Required
    var sex: Sex,
    @Required
    var birthDate: String,
    @Required
    var species: String,
    @Required
    var ownerName: String,
    @Required
    var ownerAddress: String,
    @Required
    var ownerPhoneNumber: String,

    var diagnostic: String? = "None",
    var diagnosticSymptoms: List<String>? = listOf(),
    var diagnosticNotes: List<String>? = listOf(),

    var chartDataWeight: List<ApiChartData>? = listOf(),
    var chartDataMuscle: List<ApiChartData>? = listOf(),
) {
    companion object {
        val EXAMPLE = PatientCreate(
            "Bello",
            100.0f,
            "Is a nice dog ;)",
            Sex.MALE,
            "LocalDate(2020, 2, 21)",
            "Dog",
            "Max Mustermann",
            "Hauptstraße 1",
            "123456789",
        )
    }
}
