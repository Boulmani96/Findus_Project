package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.R
import java.text.DateFormat
import java.util.*

/**
 * Shows the operation date of the selected patient on the dashboard screen.
 */
@Composable
fun SurgeryCard(title: String, operationTime: Date){
    Row(
        modifier = Modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_row_dashboard_all),
                top = dimensionResource(id = R.dimen.padding_row_dashboard_all)
            )
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.all_padding)),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Column(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterVertically)) {
            Text(text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
            )
            CompositionLocalProvider( LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = DateFormat.getDateInstance(DateFormat.MEDIUM).format(operationTime)+" | "+DateFormat.getTimeInstance(DateFormat.SHORT).format(operationTime),
                    fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp,
                    style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}