package de.h_da.fbi.findus.entities

import kotlinx.serialization.Serializable

@Serializable
data class ApiAnamnese(
    var id: String,
    var date: String,
    var organs: List<ApiOrgan> = listOf(),
    var muscles: List<ApiMuscle> = listOf(),
) {
    companion object {
        var EXAMPLE = ApiAnamnese(
            "exampleId",
            "LocalDate(2020, 2, 21)",
            listOf(
                ApiOrgan.EXAMPLE
            ),
            listOf(
                ApiMuscle.EXAMPLE
            )
        )
    }
}
