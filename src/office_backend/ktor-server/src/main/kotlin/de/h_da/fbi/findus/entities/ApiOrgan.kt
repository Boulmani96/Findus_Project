package de.h_da.fbi.findus.entities

import de.h_da.fbi.findus.database.models.Status
import de.h_da.fbi.findus.database.models.StatusChanged
import de.h_da.fbi.findus.database.models.Type
import kotlinx.serialization.Serializable

@Serializable
data class ApiOrgan(
    var name: String,
    var status: Status,
    var statusChanged: StatusChanged,
    var comment: String,
    var type: Type
) {
    companion object {
        val EXAMPLE = ApiOrgan(
            name = "heart",
            status = Status.UNKNOWN,
            statusChanged = StatusChanged.ANECHOGEN,
            comment = "",
            type = Type.RED
        )
    }
}
