package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import de.h_da.fbi.findus.database.models.Sex
import kotlinx.serialization.Serializable

@Request("A request to update a patient.")
@Serializable
data class PatientUpdate(
    var name: String?,
    var weight: Float?,
    var comments: String?,
    var sex: Sex?,

    var birthDate: String?,
    var species: String?,
    var ownerName: String?,
    var ownerAddress: String?,
    var ownerPhoneNumber: String?,

    var diagnostic: String? = "None",
    var diagnosticSymptoms: List<String>? = listOf(),
    var diagnosticNotes: List<String>? = listOf(),

    var chartDataWeight: List<ApiChartData>? = listOf(),
    var chartDataMuscle: List<ApiChartData>? = listOf(),
) {
    companion object {
        val EXAMPLE = PatientUpdate(
            "Bello",
            100.0f,
            "Is a nice dog ;)",
            Sex.MALE,
            " LocalDate(2020, 2, 21)",
            "Dog",
            "Max Mustermann",
            "Hauptstraße 1",
            "123456789",
        )
    }
}
