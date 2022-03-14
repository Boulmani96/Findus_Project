package de.h_da.fbi.common.entity

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class PatientCreate(
    var name: String,
    var weight: Float,
    var comments: String,
    var sex: Sex,
    var birthDate: String,
    var species: String,
    var ownerName: String,
    var ownerAddress: String,
    var ownerPhoneNumber: String,

    var diagnostic: String? = "None",
    var diagnosticSymptoms: List<String>? = listOf(),
    var diagnosticNotes: List<String>? = listOf(),

    var chartDataWeight: List<ApiChartData>? = listOf(),
    var chartDataMuscle: List<ApiChartData>? = listOf(),
)
