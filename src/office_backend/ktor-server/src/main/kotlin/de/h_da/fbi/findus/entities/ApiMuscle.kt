package de.h_da.fbi.findus.entities

import de.h_da.fbi.findus.database.models.Status
import de.h_da.fbi.findus.database.models.Type
import kotlinx.serialization.Serializable

@Serializable
data class ApiMuscle(
    var name: String,
    var status: Status,
    var comment: String,
    var type: Type,
    val checkedStateInjury: Boolean,
    val checkedStateSwelling: Boolean,
    val checkedStateHematoma: Boolean
) {
    companion object {
        val EXAMPLE = ApiMuscle(
            name = "muscle",
            status = Status.CHANGED,
            comment = "",
            type = Type.GREEN,
            checkedStateInjury = false,
            checkedStateSwelling = false,
            checkedStateHematoma = true
        )
    }
}
