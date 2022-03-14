package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.content.type.binary.BinaryResponse
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.io.InputStream

/**
 * Export file a class that holds a file-inputStream that can be used to download a file, in this case a CSV file
 *
 * @property csvData the CSV file that should get downloaded
 * @constructor Creates a valid empty Export file object
 */
@BinaryResponse(["text/csv"])
@Serializable
data class ExportFile(
    @Contextual
    val csvData: InputStream,
) {
    companion object {
        val EXAMPLE = ExportFile(InputStream.nullInputStream())
    }
}
