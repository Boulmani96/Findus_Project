package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.R

/**
 * Shows the body mapping images of the selected patient on the dashboard screen.
 */
@Composable
fun BodyMappingCard(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.all_padding))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.all_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_row_dashboard_body_mapping_card))){
            Button(
                enabled = index != 0,
                onClick = {
                    index--
                }
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowLeft,
                    "",
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_dashboard_body_mapping_card)))
            }
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.space_row_dashboard_body_mapping_card)))
            Text(text = listOfImages()[index].aText,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
            )
            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.space_row_dashboard_body_mapping_card)))
            Button(
                onClick = {
                    index++
                },
                enabled = index <= listOfImages().size-2,
            ) {
                Icon(
                    Icons.Filled.KeyboardArrowRight,
                    " ",
                    modifier = Modifier.size(dimensionResource(id = R.dimen.size_icon_dashboard_body_mapping_card)))
            }
        }
        Image(
            painter = painterResource(id = listOfImages()[index].aImageRes),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}