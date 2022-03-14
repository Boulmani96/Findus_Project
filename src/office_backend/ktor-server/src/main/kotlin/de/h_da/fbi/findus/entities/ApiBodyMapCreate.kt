package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import kotlinx.serialization.Serializable

@Request("A request to create a bodyMap")
@Serializable
data class ApiBodyMapCreate(
    val bodyMapFieldValues: List<ApiFieldValues>
) {
    companion object {
        val EXAMPLE = ApiBodyMapCreate(
            listOf(
                ApiFieldValues(
                    id = "1",
                    painIntensity = 0,
                    tensionIntensity = 0
                )
            )
        )
    }
}
