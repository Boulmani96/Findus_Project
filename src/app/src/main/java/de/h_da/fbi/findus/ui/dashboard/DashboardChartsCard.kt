package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.charts.BarChartAnimation
import de.h_da.fbi.findus.charts.LineChartAnimation
import de.h_da.fbi.findus.ui.selectedPatient

var WEIGHT_ROW_DASHBOARD_CHARTS_CARD_IMAGE_BUTTON = 0.3F
var WEIGHT_ROW_DASHBOARD_CHARTS_CARD_TITLE = 0.4F

/**
 * Shows a line or bar charts of weight or muscle data of the selected patient on the dashboard screen.
 */
@Composable
fun ChartsCard(){
    val dataWeightTitle = stringResource(id = R.string.dashboard_txt_charts_title_data_weight)
    var chartsStats by remember { mutableStateOf(false) }
    var weightMuscleStats by remember { mutableStateOf(false) }
    var chartsTitle : String by remember { mutableStateOf(dataWeightTitle) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.all_padding))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.all_clip_roundedcornershape)))
            .background(MaterialTheme.colors.primary)
            .padding(dimensionResource(id = R.dimen.padding_background_dashboard_chartscard))
    ){
        Column {
            Row {
                Row(modifier = Modifier
                    .weight(WEIGHT_ROW_DASHBOARD_CHARTS_CARD_IMAGE_BUTTON)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start) {
                    Button(
                        onClick = { weightMuscleStats = false },
                        enabled = weightMuscleStats,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.dashboardchartscard_icon_weight),
                            contentDescription = "",
                            Modifier.size(dimensionResource(id = R.dimen.size_image_dashboard_chartscard))
                        )
                    }

                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.all_padding)))

                    Button(
                        onClick = { weightMuscleStats = true },
                        enabled = !weightMuscleStats,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dashboardchartscard_icon_muscle),
                            contentDescription = "",
                            Modifier.size(dimensionResource(id = R.dimen.size_image_dashboard_chartscard))
                        )
                    }
                }
                Row(modifier = Modifier
                    .weight(WEIGHT_ROW_DASHBOARD_CHARTS_CARD_TITLE)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = chartsTitle,
                        fontSize = dimensionResource(id = R.dimen.all_font_size_txt).value.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = Color.White,
                        style = MaterialTheme.typography.body1
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(WEIGHT_ROW_DASHBOARD_CHARTS_CARD_IMAGE_BUTTON)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Button(
                        onClick = { chartsStats = false },
                        enabled = chartsStats,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dashboardchartscard_line_icon),
                            contentDescription = "",
                            Modifier.size(dimensionResource(id = R.dimen.size_image_dashboard_chartscard))
                        )
                    }
                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.all_padding)))
                    Button(
                        onClick = { chartsStats = true },
                        enabled = !chartsStats,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dashboardchartscard_bar_icon),
                            contentDescription = "",
                            Modifier.size(dimensionResource(id = R.dimen.size_image_dashboard_chartscard))
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.bottom_padding_space_dashboard_charts_card)))

            val dataWeightList = mutableListOf<Pair<Float, String>>()
            selectedPatient?.chartDataWeight?.forEach {
                dataWeightList += Pair(it.value, it.key)
            }

            val dataMuscleList = mutableListOf<Pair<Float, String>>()
            selectedPatient?.chartDataMuscle?.forEach {
                dataMuscleList += Pair(it.value, it.key)
            }

            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                when (chartsStats) {
                    false -> LineChartAnimation(
                        if (!weightMuscleStats) {
                            chartsTitle = stringResource(id = R.string.dashboard_txt_charts_title_data_weight)
                            dataWeightList
                        } else {
                            chartsTitle = stringResource(id = R.string.dashboard_txt_charts_title_data_muscle)
                            dataMuscleList
                        }
                    )
                    true -> BarChartAnimation(
                        if (!weightMuscleStats) {
                            chartsTitle = stringResource(id = R.string.dashboard_txt_charts_title_data_weight)
                            dataWeightList
                        } else {
                            chartsTitle = stringResource(id = R.string.dashboard_txt_charts_title_data_muscle)
                            dataMuscleList
                        }
                    )
                }
            }
        }
    }
}