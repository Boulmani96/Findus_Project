package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    var ok: Boolean,
    var token: String?,
    val message: String
)
