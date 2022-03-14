package de.h_da.fbi.findus.ui.bodymapping


import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.h_da.fbi.common.entity.ApiFieldValues
import kotlinx.coroutines.launch


class OnDragField(

    val id: Int,
    val posX: Float,
    val posY: Float,
    val rotation: Float

) {

    val basefieldcolor = Color.Gray
    var fieldcolor = Animatable(basefieldcolor)

    val fieldheight = 100f
    val fieldwidth = 50f
    var alpha = androidx.compose.animation.core.Animatable(0.3f)

    val center: Offset = Offset(posX + (fieldwidth / 2), posY + (fieldheight / 2))

    var filledPain = mutableStateOf(false)
    var filledTension = mutableStateOf(false)
    var colorChangevisiblePain = mutableStateOf(false)
    var colorChangevisibleTension = mutableStateOf(false)

    var painintensity = 0
    var tensionintensity = 0
    var currentlyselected = false

    /**
     * Color setter for an OnDragField
     * @param newColor New color that should be applied
     */
    suspend fun setColor(newColor: Color) {

        fieldcolor.animateTo(newColor)
    }

    /**
     * Alpha setter for an OnDragField that sets 100% alpha
     */
    suspend fun setAlphaFull() {
        alpha.animateTo(1f)
    }

    /**
     * Alpha setter for an OnDragField that sets a low alpha value
     */
    suspend fun setAlphaHalf() {
        alpha.animateTo(0.3f)
    }

    /**
     * Color setter for the slider of an OnDragField
     */
    suspend fun setSliderColor() {

        val color: Color

        if ((painintensity == 0) && (tensionintensity == 0)) {
            color = basefieldcolor
            fieldcolor.animateTo(color)
        } else if ((painintensity == 1) && (tensionintensity == 0)) {
            color = Color(0xFFFF8989)
            fieldcolor.animateTo(color)
        } else if ((painintensity == 2) && (tensionintensity == 0)) {
            color = Color(0xfFED4B4B)
            fieldcolor.animateTo(color)
        } else if ((painintensity == 3) && (tensionintensity == 0)) {
            color = Color(0xFF990000)
            fieldcolor.animateTo(color)

        } else if ((painintensity == 0) && (tensionintensity == 1)) {
            color = Color(0xFFAFAFFF)
            fieldcolor.animateTo(color)


        } else if ((painintensity == 0) && (tensionintensity == 2)) {
            color = Color(0xFF6666FF)
            fieldcolor.animateTo(color)


        } else if ((painintensity == 0) && (tensionintensity == 3)) {
            color = Color(0xFF2525FF)
            fieldcolor.animateTo(color)


        } else if ((painintensity == 1) && (tensionintensity == 1)) {
            color = Color(0xFFB589D6)
            fieldcolor.animateTo(color)


        } else if ((painintensity == 2) && (tensionintensity == 1) || (painintensity == 1) && (tensionintensity == 2)) {
            color = Color(0xFF9969C7)
            fieldcolor.animateTo(color)

        } else if ((painintensity == 2) && (tensionintensity == 2)) {
            color = Color(0xFF804FB3)
            fieldcolor.animateTo(color)

        } else if ((painintensity == 3) && (tensionintensity == 2) || (painintensity == 2) && (tensionintensity == 3)) {
            color = Color(0xFF6A359C)
            fieldcolor.animateTo(color)

        } else if ((painintensity == 3) && (tensionintensity == 3)) {
            color = Color(0xFF552586)
            fieldcolor.animateTo(color)

        } else if ((painintensity == 3) && (tensionintensity == 1) || (painintensity == 1) && (tensionintensity == 3)) {
            color = Color(0xFF804FB3)
            fieldcolor.animateTo(color)

        }


    }

    /**
     * Creates an intensity slider for pain
     */
    @Composable
    fun ColorSliderPain() {

        if (colorChangevisiblePain.value) {
            val BOX_HEIGHT = 125
            val BOX_WIDTH = 150

            if (painintensity == 0) {
                painintensity = 1
            }

            val scope = rememberCoroutineScope()

            LaunchedEffect(true) {
                setSliderColor()
            }

            val posXOffset = (posX.toInt() - (BOX_WIDTH + 90))
            val posYOffset = posY.toInt() - (BOX_HEIGHT / 2)

            Box(Modifier
                .offset {
                    IntOffset(
                        posXOffset,
                        posYOffset
                    )
                }
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                .height(BOX_HEIGHT.dp)
                .width(BOX_WIDTH.dp)
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(20)
                )

            ) {
                var sliderPosition by remember { mutableStateOf(painintensity.toFloat()) }

                Column {
                    Text(
                        text = "Schmerzstufe: " + (sliderPosition.toInt()).toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)

                    )

                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 1f..3f,
                        onValueChangeFinished = {
                            // launch some business logic update with the state you hold
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                            painintensity = sliderPosition.toInt()
                            scope.launch {

                                setSliderColor()

                            }

                        },
                        steps = 1,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Gray,
                            activeTrackColor = Color.Red,
                            inactiveTrackColor = Color.Red
                        )
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        TextButton(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(20)
                                )
                                .height(40.dp)
                                .width(60.dp),
                            onClick = {
                                colorChangevisiblePain.value = false
                            }) {
                            Text(
                                text = "Fertig",
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.secondaryVariant,
                                    shape = RoundedCornerShape(20)
                                )
                                .height(40.dp)
                                .width(65.dp),


                            onClick = {

                                scope.launch {
                                    painintensity = 0
                                    filledPain.value = false
                                    if (!filledPain.value && !filledTension.value) {
                                        setAlphaHalf()
                                    }
                                    setSliderColor()
                                    colorChangevisiblePain.value = false
                                }

                            }) {
                            Text(
                                text = "Löschen",
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.onSecondary
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates an intensity slider for tension
     */
    @Composable
    fun ColorSliderTension() {

        if (colorChangevisibleTension.value) {
            val BOX_HEIGHT = 125
            val BOX_WIDTH = 150

            if (tensionintensity == 0) {
                tensionintensity = 1
            }

            val scope = rememberCoroutineScope()

            LaunchedEffect(true) {
                setSliderColor()
            }

            val posXOffset = if (colorChangevisiblePain.value) {
                (posX.toInt() + (BOX_WIDTH - 80))
            } else {
                (posX.toInt() - (BOX_WIDTH + 90))
            }
            val posYOffset = posY.toInt() - (BOX_HEIGHT / 2)

            Box(Modifier
                .offset {
                    IntOffset(
                        posXOffset,
                        posYOffset
                    )
                }
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                .height(BOX_HEIGHT.dp)
                .width(BOX_WIDTH.dp)
                .background(
                    color = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(20)
                )

            ) {
                var sliderPosition by remember { mutableStateOf(tensionintensity.toFloat()) }

                Column {
                    Text(
                        text = "Spannungsstufe: " + (sliderPosition.toInt()).toString(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        color = MaterialTheme.colors.onSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 5.dp, 0.dp, 0.dp)
                    )

                    Slider(
                        value = sliderPosition,
                        onValueChange = { sliderPosition = it },
                        valueRange = 1f..3f,
                        onValueChangeFinished = {
                            // launch some business logic update with the state you hold
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                            tensionintensity = sliderPosition.toInt()
                            scope.launch {

                                setSliderColor()

                            }

                        },
                        steps = 1,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Gray,
                            activeTrackColor = Color.Blue,
                            inactiveTrackColor = Color.Blue
                        )
                    )

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        TextButton(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = RoundedCornerShape(20)
                                )
                                .height(40.dp)
                                .width(60.dp),
                            onClick = {
                                colorChangevisibleTension.value = false
                            }) {
                            Text(
                                text = "Fertig",
                                color = MaterialTheme.colors.onPrimary,
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colors.secondaryVariant,
                                    shape = RoundedCornerShape(20)
                                )
                                .height(40.dp)
                                .width(65.dp),

                            onClick = {

                                scope.launch {
                                    tensionintensity = 0
                                    filledTension.value = false
                                    if (!filledPain.value && !filledTension.value) {
                                        setAlphaHalf()
                                    }
                                    setSliderColor()
                                    colorChangevisibleTension.value = false
                                }

                            }) {
                            Text(
                                text = "Löschen",
                                color = MaterialTheme.colors.onSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
        }
    }

    /**
     * Creates a transparent box used as a hitbox for an OnDragField
     */
    @Composable
    fun TransparentBox() {

        val BOX_SIZE = 25


        Box(
            Modifier
                .offset {
                    IntOffset(
                        center.x.toInt() - BOX_SIZE,
                        center.y.toInt() - BOX_SIZE
                    )
                }
                .background(shape = CircleShape, color = Color.Transparent)
                .size(BOX_SIZE.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (filledPain.value) {
                                colorChangevisiblePain.value = true
                            }
                            if (filledTension.value) {
                                colorChangevisibleTension.value = true
                            }
                        }
                    )

                }
        )

    }

    /**
     * Function used for initializing a field
     */
    suspend fun initializeField(fieldValues: ApiFieldValues) {

        painintensity = fieldValues.painIntensity
        tensionintensity = fieldValues.tensionIntensity

        if (painintensity > 0) {
            filledPain.value = true
        }

        if (tensionintensity > 0) {
            filledTension.value = true
        }

        if (tensionintensity > 0 || painintensity > 0) {
            setAlphaFull()
        }

        setSliderColor()
    }
}

