package de.h_da.fbi.findus.routes


import com.papsign.ktor.openapigen.APITag

enum class Tags(override val description: String) : APITag {
    EXAMINATION("medical examination")
}
