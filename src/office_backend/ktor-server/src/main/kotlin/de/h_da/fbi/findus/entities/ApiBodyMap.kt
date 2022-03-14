package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import com.papsign.ktor.openapigen.annotations.Response
import kotlinx.serialization.Serializable

@Request("A request to get a bodyMap")
@Response("A bodyMap which is stored in the database.", statusCode = 200)
@Serializable
data class ApiBodyMap(
    val id: String,
    val bodyMapFieldValues: List<ApiFieldValues>
) {
    companion object {
        val EXAMPLE = ApiBodyMap(
            "1", listOf(
                ApiFieldValues(
                    id = "1",
                    painIntensity = 1,
                    tensionIntensity = 3
                ),
                ApiFieldValues(
                    id = "2",
                    painIntensity = 3,
                    tensionIntensity = 1
                )
            )
        )
        val EXAMPLE_LIST = listOf(
            EXAMPLE,
            ApiBodyMap("2", listOf())
        )
    }
}
