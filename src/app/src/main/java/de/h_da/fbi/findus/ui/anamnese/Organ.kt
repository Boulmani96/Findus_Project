package de.h_da.fbi.findus.ui.anamnese

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.PrimaryTurquoise
import de.h_da.fbi.findus.ui.PrimaryTurquoiseLight

class Organ(
    val name: String,
) {

    enum class OrganStatus {
        UNKNOWN,
        UNCHANGED,
        CHANGED
    }

    enum class UltrasonicState {
        ANECHOGEN,
        HYPOECHOGEN,
        ISOECHOGEN,
        HYPERECHOGEN,
        UNKNOWN
    }

    enum class Severity {
        RED,
        YELLOW,
        GREEN,
        UNKNOWN
    }

    var organState = mutableStateOf(OrganStatus.UNKNOWN)
    var ultrasonicState = mutableStateOf(UltrasonicState.UNKNOWN)
    var severityState = mutableStateOf(Severity.UNKNOWN)
    var comment: String = ""

    @Composable
    fun OrganRow() {

        val  showDetails = remember { mutableStateOf(true) }
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryTurquoiseLight),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = name,
                        style = TextStyle(
                            fontSize = 32.sp,
                            color = Color.White
                        ),
                    )
                }
                IconButton(onClick = { showDetails.value = !showDetails.value }) {
                    if (showDetails.value) {
                        Icon(
                            painter = painterResource(id = R.drawable.maximize),
                            contentDescription = "maximize"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.minimize),
                            contentDescription = "minimize"
                        )
                    }

                }
            }


            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.size(16.dp))
                Text(text = "Zustand:")
                Spacer(modifier = Modifier.size(16.dp))
                Checkbox(
                    checked = (organState.value == OrganStatus.UNKNOWN),
                    onCheckedChange = {
                        organState.value = OrganStatus.UNKNOWN
                    }
                )
                Text(text = "Nicht darstellbar")
                Spacer(modifier = Modifier.size(16.dp))
                Checkbox(
                    checked = organState.value == OrganStatus.UNCHANGED,
                    onCheckedChange = {
                        organState.value = OrganStatus.UNCHANGED
                    }
                )
                Text(text = "Unverändert")
                Spacer(modifier = Modifier.size(16.dp))
                Checkbox(
                    checked = organState.value == OrganStatus.CHANGED,
                    onCheckedChange = {
                        organState.value = OrganStatus.CHANGED
                    }
                )
                Text(text = "Verändert")
            }

            if (organState.value == OrganStatus.CHANGED && showDetails.value ||
                (ultrasonicState.value != UltrasonicState.UNKNOWN ) ||
                (severityState.value != Severity.UNKNOWN))  {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    Slider()
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = ultrasonicState.value == UltrasonicState.ANECHOGEN,
                        onCheckedChange = {
                            if(ultrasonicState.value == UltrasonicState.ANECHOGEN){
                                ultrasonicState.value = UltrasonicState.UNKNOWN
                            } else {
                                ultrasonicState.value = UltrasonicState.ANECHOGEN
                            }


                        }
                    )
                    Text(text = "Anechogen")
                    Spacer(modifier = Modifier.size(16.dp))
                    Checkbox(
                        checked = ultrasonicState.value == UltrasonicState.HYPOECHOGEN,
                        onCheckedChange = {
                            if(ultrasonicState.value == UltrasonicState.HYPOECHOGEN){
                                ultrasonicState.value = UltrasonicState.UNKNOWN
                            } else {
                                ultrasonicState.value = UltrasonicState.HYPOECHOGEN
                            }
                        }
                    )
                    Text(text = "Hypoechogen")
                    Spacer(modifier = Modifier.size(16.dp))
                    Checkbox(
                        checked = ultrasonicState.value == UltrasonicState.ISOECHOGEN,
                        onCheckedChange = {
                            if(ultrasonicState.value == UltrasonicState.ISOECHOGEN){
                                ultrasonicState.value = UltrasonicState.UNKNOWN
                            } else {
                                ultrasonicState.value = UltrasonicState.ISOECHOGEN
                            }
                        }
                    )
                    Text(text = "Isoechogen")
                    Spacer(modifier = Modifier.size(16.dp))
                    Checkbox(
                        checked = ultrasonicState.value == UltrasonicState.HYPERECHOGEN,
                        onCheckedChange = {
                            if(ultrasonicState.value == UltrasonicState.HYPERECHOGEN){
                                ultrasonicState.value = UltrasonicState.UNKNOWN
                            } else {
                                ultrasonicState.value = UltrasonicState.HYPERECHOGEN
                            }
                        }
                    )
                    Text(text = "Hyperechogen")
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.size(16.dp))
                    SimpleTextField()
                }

            }

        }
    }

    @Composable
    fun Slider() {


        val startpos = when (severityState.value) {
            Severity.GREEN -> 1f
            Severity.YELLOW -> 2f
            Severity.RED -> 3f
            Severity.UNKNOWN -> 0f
        }

        var sliderPosition by remember { mutableStateOf(startpos) }


        Column() {
            Text(
                text = "Grad des Problems: " +
                        when (severityState.value) {
                            Severity.GREEN -> "Leicht"
                            Severity.YELLOW -> "Mittel"
                            Severity.RED -> "Schwer"
                            Severity.UNKNOWN -> "Unbekannt"
                        }
            )
            Slider(
                modifier = Modifier.size(width = 600.dp, height = 30.dp),
                value = sliderPosition,
                valueRange = 0f..3f,
                steps = 2,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Gray,
                    activeTrackColor =
                    when (severityState.value) {
                        Severity.GREEN -> Color.Green
                        Severity.YELLOW -> Color.Yellow
                        Severity.RED -> Color.Red
                        Severity.UNKNOWN -> Color.Gray
                    }

                ),
                onValueChange = {
                    sliderPosition = it
                    when (it) {
                        0f -> severityState.value = Severity.UNKNOWN
                        1f -> severityState.value = Severity.GREEN
                        2f -> severityState.value = Severity.YELLOW
                        3f -> severityState.value = Severity.RED
                    }
                }
            )
        }

    }

    @Composable
    fun SimpleTextField() {
        var text by remember { mutableStateOf(comment) }
        OutlinedTextField(
            modifier = Modifier.size(width = 600.dp, height = 150.dp),
            label = { Text("Problembeschreibung") },
            value = text,
            onValueChange = {
                text = it
                comment = text
            }
        )
    }
}
