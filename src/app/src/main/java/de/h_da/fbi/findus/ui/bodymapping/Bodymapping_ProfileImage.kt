package de.h_da.fbi.findus.ui.bodymapping

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.ui.patientsPhotos
import de.h_da.fbi.findus.ui.selectedPatient


/**
 * Creates an image for the profile picture of the selected patient
 */
@Composable
fun Img_profile() {
    patientsPhotos[selectedPatient?.id]?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = "patient profile picture",
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
        )
    }
}

/**
 * Creates a text for the name of the selected patient
 */
@Composable
fun Txt_name() {
    Text(
        text = selectedPatient?.name.toString(),
        modifier = Modifier
            .padding(5.dp),
        color = MaterialTheme.colors.onSecondary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Creates a text for the age picture of the selected patient
 */
@Composable
fun Txt_age() {
    Text(
        text = selectedPatient?.birthDate.toString(),
        modifier = Modifier
            .padding(5.dp),
        color = MaterialTheme.colors.onSecondary,
        fontSize = 18.sp
    )
}

