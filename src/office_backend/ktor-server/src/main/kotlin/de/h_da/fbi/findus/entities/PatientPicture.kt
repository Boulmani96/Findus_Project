package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.content.type.binary.BinaryRequest
import com.papsign.ktor.openapigen.content.type.binary.BinaryResponse
import java.io.InputStream

@BinaryResponse(["image/png", "image/jpeg"])
@BinaryRequest(["image/png", "image/jpeg"])
data class PatientPicture(
    val picture: InputStream
) {
    companion object {
        val EXAMPLE = PatientPicture(InputStream.nullInputStream())
    }
}
