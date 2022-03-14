package de.h_da.fbi.findus.entities

import de.h_da.fbi.findus.database.models.Status
import de.h_da.fbi.findus.database.models.StatusChanged
import de.h_da.fbi.findus.database.models.Type
import kotlinx.serialization.Required

data class ApiAnamneseCreateUpdate(
    @Required
    var date: String,
    @Required
    var organs: List<ApiOrgan> = listOf(),
    @Required
    var muscles: List<ApiMuscle> = listOf(),
) {
    companion object {
        var EXAMPLE = ApiAnamneseCreateUpdate(
            "LocalDate(2020, 2, 21)",
            listOf(
                ApiOrgan(
                    name = "lungs",
                    status = Status.UNKNOWN,
                    statusChanged = StatusChanged.ANECHOGEN,
                    comment = "",
                    type = Type.RED
                )
            ),
            listOf(
                ApiMuscle(
                    name = "muscle",
                    status = Status.CHANGED,
                    comment = "",
                    type = Type.GREEN,
                    checkedStateInjury = false,
                    checkedStateSwelling = false,
                    checkedStateHematoma = true
                )
            )
        )
    }
}
