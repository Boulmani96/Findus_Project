package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.h_da.fbi.common.entity.Sex
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.patientsPhotos
import de.h_da.fbi.findus.ui.selectedPatient
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Convert the given string to date
 * @param convertedDate The converted string date
 * @return date
 */
fun convertStringToDate(convertedDate: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(convertedDate)
}

/**
 * Shows the information of the selected patient on the dashboard screen.
 */
@Composable
fun ProfileCard(name: String, sex: Sex, birthDate: String){
    Row(
        modifier = Modifier
            .padding(start = dimensionResource(id = R.dimen.padding_row_dashboard_all), top = dimensionResource(id = R.dimen.padding_row_dashboard_all))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.all_padding)),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Surface(
            modifier = Modifier.size(dimensionResource(id = R.dimen.size_surface_dashboard_profile_card)),
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner_shape_dashboard_profile_card)),
            color = MaterialTheme.colors.primary
        ) {
            patientsPhotos[selectedPatient?.id]?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = " ",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = dimensionResource(id = R.dimen.all_padding))
                .align(Alignment.CenterVertically)) {
            Row {
                Text(text = name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
                )
                Image(
                    painter = if (sex == Sex.MALE) {
                        painterResource(id = R.drawable.dashboardprofilcard_male_icon)
                    }
                    else{
                        painterResource(id = R.drawable.dashboardprofilcard_female_icon)
                    },
                    contentDescription = "",
                    Modifier.size(dimensionResource(id = R.dimen.size_image_dashboard_profile_card)))

            }
            CompositionLocalProvider( LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = DateFormat.getDateInstance(DateFormat.LONG).format(convertStringToDate(birthDate)),
                    style = MaterialTheme.typography.body2,
                    fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp)
            }
        }
    }
}