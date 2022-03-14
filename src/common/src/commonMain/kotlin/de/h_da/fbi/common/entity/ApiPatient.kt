package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ApiPatient(
    var id: String,
    var name: String,
    var weight: Float,
    var comments: String,
    var sex: Sex,
    var birthDate: String,
    var species: String,
    var ownerName: String,
    var ownerAddress: String,
    var ownerPhoneNumber: String,

    var diagnostic: String,
    var diagnosticSymptoms: List<String>,
    var diagnosticNotes: List<String>,

    var chartDataWeight: List<ApiChartData>,
    var chartDataMuscle: List<ApiChartData>,

    var anamnese: List<ApiAnamnese>,
    var bodyMaps: List<ApiBodyMap>,
)
