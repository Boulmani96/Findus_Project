package de.h_da.fbi.findus.ui.bodymapping.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

class ImageUtils {

    companion object{
        fun generateImage(view: View): Bitmap {
            val imageBitmap = Bitmap.createBitmap(
                view.width,
                view.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(imageBitmap)
            view.layout(
                view.left,
                view.top,
                view.right,
                view.bottom
            )
            view.draw(canvas)
            return imageBitmap
        }
    }
}