package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiOrgan(
    var name: String,
    var status: Status,
    var statusChanged: StatusChanged,
    var comment: String,
    var type: Type
)
