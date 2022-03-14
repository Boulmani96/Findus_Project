package de.h_da.fbi.findus.ui.dashboard

import de.h_da.fbi.findus.R

/**
 * Image data class contains bodymapping image and their text
 * @param aImageRes The bodymapping image
 * @param aText The text of the image
 */
data class ImageData(var aImageRes: Int, var aText : String)

/**
 * Create a list of Bodymapping images
 */
fun listOfImages() = mutableListOf(
    ImageData(R.drawable.dashboardbodymappingcard_icon_1,"15.11.2021"),
    ImageData(R.drawable.dashboardbodymappingcard_icon_2,"14.01.2022")
)