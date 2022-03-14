package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.content.type.binary.BinaryRequest
import com.papsign.ktor.openapigen.content.type.binary.BinaryResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.InputStream

/**
 * Examination picture a picture of an animal or examination that should be uploaded
 *
 * @property picture the picture to upload
 * @constructor Creates a valid Examination picture
 */
@BinaryResponse(["image/png", "image/jpeg"])
@BinaryRequest(["image/png", "image/jpeg"])
@Serializable
data class ExaminationPicture(
    @Contextual
    val picture: InputStream,
) {
    companion object {
        val EXAMPLE = ExaminationPicture(InputStream.nullInputStream())
    }
}
