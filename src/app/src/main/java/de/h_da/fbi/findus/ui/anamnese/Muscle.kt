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

class Muscle(
    val name: String,
) {

    enum class OrganStatus {
        UNKNOWN,
        UNCHANGED,
        CHANGED
    }


    enum class Severity {
        RED,
        YELLOW,
        GREEN,
        UNKNOWN
    }

    var organState = mutableStateOf(OrganStatus.UNKNOWN)
    var severityState = mutableStateOf(Severity.UNKNOWN)
    var checkedStateInjury = mutableStateOf(false)
    var checkedStateSwelling =  mutableStateOf(false)
    var checkedStateHematome =  mutableStateOf(false)

    var comment: String = ""

    @Composable
    fun MuscleRow() {

        val showDetails = remember { mutableStateOf(true) }
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
                    checked = organState.value == OrganStatus.UNKNOWN,
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
            if (organState.value == OrganStatus.CHANGED && showDetails.value) {
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
                    Spacer(modifier = Modifier.size(16.dp))
                    Checkbox(
                        checked = checkedStateHematome.value,
                        onCheckedChange = {
                            checkedStateHematome.value = !checkedStateHematome.value
                        }
                    )
                    Text(text = "Hämatome")
                    Spacer(modifier = Modifier.size(16.dp))
                    Checkbox(
                        checked = checkedStateInjury.value,
                        onCheckedChange = {
                            checkedStateInjury.value = !checkedStateInjury.value
                        }
                    )
                    Text(text = "Verletzungen")
                    Spacer(modifier = Modifier.size(16.dp))
                    Checkbox(
                        checked = checkedStateSwelling.value,
                        onCheckedChange = {
                            checkedStateSwelling.value = !checkedStateSwelling.value
                        }
                    )
                    Text(text = "Schwellungen")


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
