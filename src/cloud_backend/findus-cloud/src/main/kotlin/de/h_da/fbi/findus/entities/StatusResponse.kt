package de.h_da.fbi.findus.entities

import kotlinx.serialization.Serializable

/**
 * Holds the status of the API call
 *
 * @property status the status that resulted from the API call
 * @constructor Creates a Status response object
 */
@Serializable
data class StatusResponse(
    val status: String,
)
