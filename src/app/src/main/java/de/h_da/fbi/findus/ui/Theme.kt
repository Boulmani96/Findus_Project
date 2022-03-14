package de.h_da.fbi.findus.ui


import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val MainThemeColors = lightColors(

    primary = PrimaryTurquoise,
    primaryVariant = PrimaryTurquoiseLight,
    secondary = SecondaryGray,
    secondaryVariant = SecondaryGrayDark,

    onPrimary = PrimaryTextColor,
    onSecondary = SecondaryTextColor
)

@Composable
fun Theme(
    content: @Composable () -> Unit
) {
    val colors = MainThemeColors
    MaterialTheme(
        colors = colors,
        content = content
    )
}
