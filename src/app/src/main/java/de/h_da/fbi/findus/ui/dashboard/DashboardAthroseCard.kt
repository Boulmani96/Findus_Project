package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.selectedPatient

/**
 * Shows the diagnostic Symptoms of the selected patient on the dashboard screen.
 */
@Composable
fun AthrosisCard(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.all_padding))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.all_padding)),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.space_column_dashboard_athoresecard)),
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.dashboard_txt_athrose),
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp
                )
            Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                selectedPatient?.diagnosticSymptoms?.forEach{
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(it,
                        fontSize = dimensionResource(id = R.dimen.all_font_size_txt_dashboard_small).value.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(dimensionResource(id = R.dimen.all_padding_small_dashboard))
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
                            .border(
                                dimensionResource(id = R.dimen.all_border),
                                Color.White
                            ).background(Color(0xFFFFFFFF))
                            .padding(dimensionResource(id = R.dimen.all_padding))
                    )
                }
            }
        }
    }
}
