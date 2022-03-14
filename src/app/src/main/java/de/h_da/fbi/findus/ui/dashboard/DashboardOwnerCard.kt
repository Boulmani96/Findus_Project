package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.R


/**
 * Shows the owner profile of the selected patient on the dashboard screen.
 */
@Composable
fun OwnerCard(name: String, phoneNumber: String){
    Row(
        modifier = Modifier
            .padding(start = dimensionResource(id = R.dimen.padding_row_dashboard_all), top = dimensionResource(id = R.dimen.padding_row_dashboard_all))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.all_padding)),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Surface(
            modifier = Modifier.size(dimensionResource(id = R.dimen.size_surface_dashboard_owner_card)),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            Image(painter = painterResource(id = R.drawable.dashboardownercard_owner_icon), contentDescription = "")
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_background_dashboard_chartscard)))
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier.height(dimensionResource(id = R.dimen.size_surface_dashboard_owner_card))) {
            Text(text = name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
            )
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(phoneNumber, style = MaterialTheme.typography.body2, fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp)
            }
        }
    }
}