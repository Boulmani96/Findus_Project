package de.h_da.fbi.findus.ui.bodymapping

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import de.h_da.fbi.findus.ui.bodymapping.utils.ImageUtils
import java.io.*

class BodyMapping_View @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    private val fileFormat = ".jpg"
    private val mimeTypeImage = "image/jpg"
    private val imageQuality = 100

    /**
     * Sets the content to capture
     */
    @Composable
    override fun Content() {
        BodymapCanvas()
    }

    /**
     * Capture of content
     * @param view View that should be captured
     */
    fun capture(view: BodyMapping_View) {
        val bitmap = ImageUtils.generateImage(view)
        saveMediaToStorage(bitmap)
    }

    /**
     * Saving content capture to storage
     * @param bitmap Bitmap that should be stored
     */
    private fun saveMediaToStorage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}$fileFormat"

        var imageOutputStream: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, mimeTypeImage)
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                imageOutputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //For devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            imageOutputStream = FileOutputStream(image)
        }

        imageOutputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, imageQuality, it)
        }
    }

}