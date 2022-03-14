package de.h_da.fbi.common.entity

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val username: String,
    val password: String,
)
