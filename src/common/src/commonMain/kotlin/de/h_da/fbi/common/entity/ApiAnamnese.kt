package de.h_da.fbi.common.entity

import kotlinx.serialization.Serializable

@Serializable
data class ApiAnamnese(
    var id: String,
    var date: String,
    var organs: MutableList<ApiOrgan> = mutableListOf(),
    var muscles: MutableList<ApiMuscle> = mutableListOf(),
)
