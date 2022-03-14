package de.h_da.fbi.findus.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import de.h_da.fbi.common.entity.ApiPatient
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.FindusViewModel
import org.koin.androidx.compose.getViewModel
import java.util.*

const val HEIGHT_DASHBOARDSCREEN_MAX = 0.35F
const val WEIGHT_DASHBOARDSCREEN_PATIENT_OWNER = 0.3F
const val WEIGHT_DASHBOARDSCREEN_EXAMINATION_OPERATION = 0.26F
const val WEIGHT_DASHBOARDSCREEN_NOTICE = 0.42F
const val WEIGHT_DASHBOARDSCREEN_ARTHROSE_CHARTS = 0.5F
const val WEIGHT_DASHBOARDSCREEN_BODYMAPPING = 0.5F

var textFieldstats: String by mutableStateOf("")
var enabeldAddButton: Boolean by mutableStateOf(false)
var index: Int by mutableStateOf(0)

/**
 * The dashboard screen where the information of the selected patient will be shown.
 * @param selectedPatient The selected patient
 * @param findusViewModel The findus view model
 */
@Composable
fun DashBoardScreen(selectedPatient: ApiPatient?, findusViewModel: FindusViewModel = getViewModel()) {
    if(selectedPatient?.name?.isNotEmpty() == true) {
        Column(modifier = Modifier
            .background(MaterialTheme.colors.secondary)
            .fillMaxHeight()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(HEIGHT_DASHBOARDSCREEN_MAX)) {
                Column(modifier = Modifier
                    .weight(WEIGHT_DASHBOARDSCREEN_PATIENT_OWNER)
                    .fillMaxHeight()) {
                    ProfileCard(selectedPatient.name, selectedPatient.sex, selectedPatient.birthDate)
                    OwnerCard(selectedPatient.ownerName, selectedPatient.ownerPhoneNumber)
                }
                Column(modifier = Modifier
                    .weight(WEIGHT_DASHBOARDSCREEN_EXAMINATION_OPERATION)
                    .fillMaxHeight()) {
                    ExaminationCard(stringResource(id = R.string.dashboard_txt_examination), generateExaminationAppointment())
                    SurgeryCard(stringResource(id = R.string.dashboard_txt_operation), generateOperationAppointment())
                }
                Column(modifier = Modifier
                    .weight(WEIGHT_DASHBOARDSCREEN_NOTICE)
                    .fillMaxHeight()) {
                    NoticeCard()
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()){
                Column(modifier = Modifier.weight(WEIGHT_DASHBOARDSCREEN_ARTHROSE_CHARTS)) {
                    AthrosisCard()
                    ChartsCard()
                }
                Column(modifier = Modifier
                    .weight(WEIGHT_DASHBOARDSCREEN_BODYMAPPING)
                    .fillMaxHeight()) {
                    BodyMappingCard()
                }
            }
        }
    } else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = stringResource(id = R.string.dashboard_description_home),
                tint = Color.Black,
                modifier = Modifier.size(dimensionResource(id = R.dimen.size_dashboard_icon))
            )
            Text(text = stringResource(id = R.string.dashboard_txt_home),
                color = Color.Black,
                fontSize = dimensionResource(id = R.dimen.font_size_txt_dashboard).value.sp)
        }
    }
}

/**
 * generate a random date for the examination appointment.
 * @return Date
 */
fun generateExaminationAppointment(): Date {
    return Date(2022-1900,1-1,28, 10,20)
}

/**
 * generate a random date for the operation appointment.
 * @return Date
 */
fun generateOperationAppointment(): Date {
    return Date(2022-1900,4-1,15, 15,40)
}
