package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiBodyMap(
    val id: String,
    val bodyMapFieldValues: List<ApiFieldValues>
)
