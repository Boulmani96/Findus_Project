package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiMuscle(
    var name: String,
    var status: Status,
    var comment: String,
    var type: Type,
    var checkedStateInjury: Boolean,
    var checkedStateSwelling: Boolean,
    var checkedStateHematoma: Boolean
)
