package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response
import kotlinx.serialization.Serializable

@Response("A response with just text.")
@Serializable
data class StatusResponse(
    val status: String?,
)
