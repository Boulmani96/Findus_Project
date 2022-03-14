package de.h_da.fbi.findus.entities

import kotlinx.serialization.Serializable

@Serializable
data class ApiChartData(
    val key: String,
    val value: Float,
)
