package de.h_da.fbi.common.entity

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class ApiAnamneseCreateUpdate(
    var date: String,
    var organs: MutableList<ApiOrgan> = mutableListOf(),
    var muscles: MutableList<ApiMuscle> = mutableListOf(),
)
