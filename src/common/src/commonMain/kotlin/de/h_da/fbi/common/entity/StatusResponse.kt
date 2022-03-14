package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class StatusResponse(
    val status: String?,
)
