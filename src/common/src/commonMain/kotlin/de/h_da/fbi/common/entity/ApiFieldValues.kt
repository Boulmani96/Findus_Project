package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiFieldValues(
    val id: String,
    val painIntensity: Int,
    val tensionIntensity: Int
)
