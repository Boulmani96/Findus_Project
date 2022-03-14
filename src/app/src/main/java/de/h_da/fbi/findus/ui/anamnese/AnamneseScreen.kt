package de.h_da.fbi.findus.ui.anamnese

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.*
import org.koin.androidx.compose.getViewModel

//for future use, screen implementation
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import de.h_da.fbi.common.entity.*
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.*
import kotlinx.coroutines.launch
import java.util.*
import de.h_da.fbi.common.entity.ApiPatient
import de.h_da.fbi.findus.ui.Theme

enum class RowStatus {
    MUSCLE,
    ORGAN
}

var organList = mutableListOf<Organ>()
var muscleList = mutableListOf<Muscle>()
var switchRow = mutableStateOf(RowStatus.ORGAN)

@Composable
fun AnamneseScreen(selectedPatient: ApiPatient?, findusViewModel: FindusViewModel = getViewModel<FindusViewModel>()) {
    Theme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Anamnesebogen vom: "+ currentAnamnese.date) })
            }
        ) {
            AnamnesisDetail()
        }
    }

}

@Composable
fun AnamnesisDetail(){
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()
    Column (
        modifier =  Modifier.background(SecondaryGrayLight)
            ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                horizontalArrangement = Arrangement.Start
            ){
                IconButton(onClick = {
                }) {
                    Icon(painter = painterResource(id = R.drawable.filter), contentDescription = "filter", modifier = Modifier.size(50.dp))
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {switchRow.value = RowStatus.ORGAN},
                    colors = ButtonDefaults.buttonColors(backgroundColor =
                    if(switchRow.value == RowStatus.ORGAN){
                        PrimaryTurquoiseLight}
                    else{ SecondaryGray}))
                 {

                    Text(
                        text = "Innere Organe",
                        color = Color.Black,
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { switchRow.value = RowStatus.MUSCLE },
                    colors = ButtonDefaults.buttonColors(backgroundColor =
                    if(switchRow.value == RowStatus.MUSCLE){
                        PrimaryTurquoiseLight}
                    else{ SecondaryGray}))
                {
                    Text(
                        text = "Muskeln",
                        color = Color.Black,
                        style = TextStyle(fontSize = 20.sp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {
                }) {
                    Icon(painter = painterResource(id = R.drawable.deleteforever), contentDescription = "delete", modifier = Modifier.size(50.dp))
                }
                IconButton(onClick = {
                    val tmpOrganList = mutableListOf<ApiOrgan>()
                    for (organ in organList) {
                        tmpOrganList.add(OrganCasterNormalToApi(organ))
                    }

                    val tmpMuscleList = mutableListOf<ApiMuscle>()
                    for (muscle in muscleList) {
                        tmpMuscleList.add(MuscleCasterNormalToApi(muscle))
                    }

                    val anamneseUpdate = ApiAnamneseCreateUpdate(
                        currentAnamnese.date, tmpOrganList, tmpMuscleList)
                    coroutineScope.launch{
                        val saveResponse = repo.patchAnamnese(
                            currentPatient.id, currentAnamnese.id, anamneseUpdate)

                    }

                    Toast.makeText(
                        context,
                        "successfully saved Anamnesis",
                        Toast.LENGTH_SHORT
                    ).show()
                 }) {
                    Icon(painter = painterResource(id = R.drawable.save), contentDescription = "save", modifier = Modifier.size(50.dp))
                }
            }
        }
        Divider(color = Color.Black, thickness = 1.dp)
        AnamneseList()
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun AnamneseList(){
    val scrollState = rememberScrollState()
    organList.clear()
    for(Api_Organ_i in currentAnamnese.organs){
        var currentOrgan = OrganCasterApiToNormal(Api_Organ_i)
        organList.add(currentOrgan)
    }
    muscleList.clear()
    for(Api_Muscle_i in currentAnamnese.muscles){
        var currentMuscle = MuscleCasterApiToNormal(Api_Muscle_i)
        muscleList.add(currentMuscle)
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(vertical = 2.dp)
    ) {

        when(switchRow.value){
            RowStatus.MUSCLE ->  for(muscle in muscleList) {
                Spacer(modifier = Modifier.size(10.dp))
                muscle.MuscleRow()
                Spacer(modifier = Modifier.size(10.dp))
            }

            RowStatus.ORGAN -> for(organ in organList) {
                Spacer(modifier = Modifier.size(10.dp))
                organ.OrganRow()
                Spacer(modifier = Modifier.size(10.dp))
            }
        }

        }

}

/**
 * casts ApiOrgan.kt object to Organ.kt with correct data
 * @param ApiOrgan object to be casted
 * @return Organ object is returned
 */
fun OrganCasterApiToNormal(Api_Organ: ApiOrgan): Organ{
    var resultOrgan = Organ(Api_Organ.name)
    when(Api_Organ.status){
        Status.UNKNOWN -> resultOrgan.organState = mutableStateOf(Organ.OrganStatus.UNKNOWN)
        Status.CHANGED -> resultOrgan.organState = mutableStateOf(Organ.OrganStatus.CHANGED)
        Status.UNCHANGED -> resultOrgan.organState = mutableStateOf(Organ.OrganStatus.UNCHANGED)
    }
    when(Api_Organ.statusChanged){
        StatusChanged.UNKNOWN -> resultOrgan.ultrasonicState = mutableStateOf(Organ.UltrasonicState.UNKNOWN)
        StatusChanged.ANECHOGEN -> resultOrgan.ultrasonicState = mutableStateOf(Organ.UltrasonicState.ANECHOGEN)
        StatusChanged.HYPERECHOGEN -> resultOrgan.ultrasonicState = mutableStateOf(Organ.UltrasonicState.HYPERECHOGEN)
        StatusChanged.HYPOECHOGEN -> resultOrgan.ultrasonicState = mutableStateOf(Organ.UltrasonicState.HYPOECHOGEN)
        StatusChanged.ISOECHOGEN -> resultOrgan.ultrasonicState = mutableStateOf(Organ.UltrasonicState.ISOECHOGEN)
    }
    when(Api_Organ.type){
        Type.UNKNOWN -> resultOrgan.severityState = mutableStateOf(Organ.Severity.UNKNOWN)
        Type.GREEN -> resultOrgan.severityState = mutableStateOf(Organ.Severity.GREEN)
        Type.YELLOW -> resultOrgan.severityState = mutableStateOf(Organ.Severity.YELLOW)
        Type.RED -> resultOrgan.severityState = mutableStateOf(Organ.Severity.RED)
    }
    resultOrgan.comment = Api_Organ.comment
    return resultOrgan
}

/**
 * casts Organ.kt object to ApiOrgan.kt with correct data
 * @param currentOrgan object to be casted
 * @return ApiOrgan object is returned
 */
fun OrganCasterNormalToApi(currentOrgan: Organ): ApiOrgan{
    val resultApiOrgan = ApiOrgan(currentOrgan.name, Status.UNKNOWN, StatusChanged.UNKNOWN, currentOrgan.comment, Type.UNKNOWN)
    when(currentOrgan.organState.value){
        Organ.OrganStatus.UNKNOWN -> resultApiOrgan.status = Status.UNKNOWN
        Organ.OrganStatus.UNCHANGED -> resultApiOrgan.status = Status.UNCHANGED
        Organ.OrganStatus.CHANGED -> resultApiOrgan.status = Status.CHANGED
    }
    when(currentOrgan.ultrasonicState.value){
        Organ.UltrasonicState.UNKNOWN -> resultApiOrgan.statusChanged = StatusChanged.UNKNOWN
        Organ.UltrasonicState.ANECHOGEN -> resultApiOrgan.statusChanged = StatusChanged.ANECHOGEN
        Organ.UltrasonicState.ISOECHOGEN -> resultApiOrgan.statusChanged = StatusChanged.ISOECHOGEN
        Organ.UltrasonicState.HYPOECHOGEN -> resultApiOrgan.statusChanged = StatusChanged.HYPOECHOGEN
        Organ.UltrasonicState.HYPERECHOGEN -> resultApiOrgan.statusChanged = StatusChanged.HYPERECHOGEN
    }
    when(currentOrgan.severityState.value){
        Organ.Severity.UNKNOWN -> resultApiOrgan.type = Type.UNKNOWN
        Organ.Severity.GREEN -> resultApiOrgan.type = Type.GREEN
        Organ.Severity.YELLOW -> resultApiOrgan.type = Type.YELLOW
        Organ.Severity.RED -> resultApiOrgan.type = Type.RED
    }
    return resultApiOrgan
}

/**
 * casts ApiMuscle.kt object to Muscle.kt with correct data
 * @param Api_Muscle object to be casted
 * @return Muscle object is returned
 */
fun MuscleCasterApiToNormal(Api_Muscle: ApiMuscle): Muscle{
    var resultMuscle = Muscle(Api_Muscle.name)
    when(Api_Muscle.status){
        Status.UNKNOWN -> resultMuscle.organState.value = Muscle.OrganStatus.UNKNOWN
        Status.CHANGED -> resultMuscle.organState.value = Muscle.OrganStatus.CHANGED
        Status.UNCHANGED -> resultMuscle.organState.value = Muscle.OrganStatus.UNCHANGED
    }
    when(Api_Muscle.type){
        Type.UNKNOWN -> resultMuscle.severityState.value = Muscle.Severity.UNKNOWN
        Type.GREEN -> resultMuscle.severityState.value = Muscle.Severity.GREEN
        Type.YELLOW -> resultMuscle.severityState.value = Muscle.Severity.YELLOW
        Type.RED -> resultMuscle.severityState.value = Muscle.Severity.RED
    }
    resultMuscle.checkedStateInjury = mutableStateOf(Api_Muscle.checkedStateInjury)
    resultMuscle.checkedStateHematome = mutableStateOf(Api_Muscle.checkedStateHematoma)
    resultMuscle.checkedStateSwelling = mutableStateOf(Api_Muscle.checkedStateSwelling)
    resultMuscle.comment = Api_Muscle.comment
    return resultMuscle
}

/**
 * casts Muscle.kt object to ApiMuscle.kt with correct data
 * @param currentMuscle object to be casted
 * @return ApiMuscle object is returned
 */
fun MuscleCasterNormalToApi(currentMuscle: Muscle): ApiMuscle{
    var resultApiMuscle = ApiMuscle(currentMuscle.name, Status.UNKNOWN, currentMuscle.comment, Type.UNKNOWN,
        false, false, false)

    when(currentMuscle.organState.value){
        Muscle.OrganStatus.UNKNOWN -> resultApiMuscle.status = Status.UNKNOWN
        Muscle.OrganStatus.UNCHANGED -> resultApiMuscle.status = Status.UNCHANGED
        Muscle.OrganStatus.CHANGED -> resultApiMuscle.status = Status.CHANGED
    }
    when(currentMuscle.severityState.value){
        Muscle.Severity.UNKNOWN -> resultApiMuscle.type = Type.UNKNOWN
        Muscle.Severity.GREEN -> resultApiMuscle.type = Type.GREEN
        Muscle.Severity.YELLOW -> resultApiMuscle.type = Type.YELLOW
        Muscle.Severity.RED -> resultApiMuscle.type = Type.RED
    }
    when(currentMuscle.checkedStateInjury.value){
        true -> resultApiMuscle.checkedStateInjury = true
        false -> resultApiMuscle.checkedStateInjury = false
    }
    when(currentMuscle.checkedStateSwelling.value){
        true -> resultApiMuscle.checkedStateSwelling = true
        false -> resultApiMuscle.checkedStateSwelling = false
    }
    when(currentMuscle.checkedStateHematome.value){
        true -> resultApiMuscle.checkedStateHematoma = true
        false -> resultApiMuscle.checkedStateHematoma = false
    }
    return resultApiMuscle
}
