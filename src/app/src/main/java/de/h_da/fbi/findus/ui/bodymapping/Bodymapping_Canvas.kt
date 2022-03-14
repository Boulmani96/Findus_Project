package de.h_da.fbi.findus.ui.bodymapping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.selectedPatient


/**
 * Creates a canvas for the bodymap with all OnDragFields and Draggables
 */
@Composable
fun BodymapCanvas() {

    var fieldList: List<OnDragField> = emptyList()
    val toolbarColor = MaterialTheme.colors.secondaryVariant
    val outlineSizeRatio = 0.6f
    val outlineOffset = 0.425f
    var outlineYOffset by remember { mutableStateOf(0f) }
    var canvasSize by remember {
        mutableStateOf(Size(0f, 0f))
    }
    var outlineSize by remember {
        mutableStateOf(Size(0f, 0f))
    }
    val toolbarWidth = outlineSize.width * 0.03
    val toolbarHeight = outlineSize.height / 2


    Box(

        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(color = Color.Transparent)
            .paint(painterResource(id = R.drawable.dog_outline), contentScale = ContentScale.Fit)
            .drawBehind
            {

                canvasSize = size
                outlineSize = Size(
                    canvasSize.width,
                    (canvasSize.width * outlineSizeRatio)
                )
                outlineYOffset = (canvasSize.height - outlineSize.height) / 2

                if (outlineSize.width > 0 || outlineSize.height > 0) {
                    for (field in fieldList) {
                        rotate(field.rotation, field.center) {

                            drawOval(
                                color = field.fieldcolor.value,
                                topLeft = Offset(field.posX, field.posY),
                                size = Size(field.fieldwidth, field.fieldheight),
                                alpha = field.alpha.value

                            )
                        }
                    }
                }
            }
    ) {

        if (outlineSize.width > 0 || outlineSize.height > 0) {

            fieldList = listOf(

                //##TOP VIEW##
                //Top view, left front paw
                OnDragField(
                    1,
                    (outlineSize.width * 0.13).toFloat(),
                    (outlineSize.height * 0.03 + outlineYOffset).toFloat(),
                    160f
                ),
                //Top view, left lower arm
                OnDragField(
                    2,
                    (outlineSize.width * 0.155).toFloat(),
                    (outlineSize.height * 0.1375 + outlineYOffset).toFloat(),
                    160f
                ),
                //Top view, left upper arm
                OnDragField(
                    3,
                    (outlineSize.width * 0.21).toFloat(),
                    (outlineSize.height * 0.175 + outlineYOffset).toFloat(),
                    90f
                ),

                OnDragField(
                    4,
                    (outlineSize.width * 0.19).toFloat(),
                    (outlineSize.height * 0.2475 + outlineYOffset).toFloat(),
                    90f
                ),
                //Top view, left shoulder to back
                OnDragField(
                    5,
                    (outlineSize.width * 0.245).toFloat(),
                    (outlineSize.height * 0.22 + outlineYOffset).toFloat(),
                    30f
                ),
                //Top view, neck
                OnDragField(
                    6,
                    (outlineSize.width * 0.27).toFloat(),
                    (outlineSize.height * 0.14 + outlineYOffset).toFloat(),
                    90f
                ),
                //Top view, snout
                OnDragField(
                    7,
                    (outlineSize.width * 0.27).toFloat(),
                    outlineYOffset,
                    0f
                ),
                //Top view, left side back
                OnDragField(
                    8,
                    (outlineSize.width * 0.22).toFloat(),
                    (outlineSize.height * 0.33 + outlineYOffset).toFloat(),
                    170f
                ),
                OnDragField(
                    9,
                    (outlineSize.width * 0.235).toFloat(),
                    (outlineSize.height * 0.445 + outlineYOffset).toFloat(),
                    170f
                ),
                //Top view, left upper leg
                OnDragField(
                    10,
                    (outlineSize.width * 0.21).toFloat(),
                    (outlineSize.height * 0.7 + outlineYOffset).toFloat(),
                    135f
                ),
                //Top view, left lower leg
                OnDragField(
                    11,
                    (outlineSize.width * 0.185).toFloat(),
                    (outlineSize.height * 0.765 + outlineYOffset).toFloat(),
                    15f
                ),
                //Top view, left back paw
                OnDragField(
                    12,
                    (outlineSize.width * 0.14).toFloat(),
                    (outlineSize.height * 0.86 + outlineYOffset).toFloat(),
                    55f
                ),
                //Top view, head
                OnDragField(
                    13,
                    (outlineSize.width * 0.27).toFloat(),
                    (outlineSize.height * 0.0825 + outlineYOffset).toFloat(),
                    90f
                ),
                //Top view, tail
                OnDragField(
                    14,
                    (outlineSize.width * 0.2737).toFloat(),
                    (outlineSize.height * 0.78 + outlineYOffset).toFloat(),
                    0f
                ),

                //Top view, right front paw
                OnDragField(
                    15,
                    (outlineSize.width * 0.41).toFloat(),
                    (outlineSize.height * 0.04 + outlineYOffset).toFloat(),
                    20f
                ),
                //Top view, right lower arm
                OnDragField(
                    16,
                    (outlineSize.width * 0.382).toFloat(),
                    (outlineSize.height * 0.1425 + outlineYOffset).toFloat(),
                    20f
                ),
                //Top view, right upper arm
                OnDragField(
                    17,
                    (outlineSize.width * 0.325).toFloat(),
                    (outlineSize.height * 0.175 + outlineYOffset).toFloat(),
                    90f
                ),
                OnDragField(
                    18,
                    (outlineSize.width * 0.34).toFloat(),
                    (outlineSize.height * 0.2475 + outlineYOffset).toFloat(),
                    90f
                ),
                //Top view, right shoulder to back
                OnDragField(
                    19,
                    (outlineSize.width * 0.2925).toFloat(),
                    (outlineSize.height * 0.22 + outlineYOffset).toFloat(),
                    150f
                ),
                //Top view, right side back
                OnDragField(
                    20,
                    (outlineSize.width * 0.31).toFloat(),
                    (outlineSize.height * 0.33 + outlineYOffset).toFloat(),
                    10f
                ),
                OnDragField(
                    21,
                    (outlineSize.width * 0.30).toFloat(),
                    (outlineSize.height * 0.445 + outlineYOffset).toFloat(),
                    10f
                ),
                //Top view, right upper leg
                OnDragField(
                    22,
                    (outlineSize.width * 0.34).toFloat(),
                    (outlineSize.height * 0.7085 + outlineYOffset).toFloat(),
                    45f
                ),
                //Top view, right lower leg
                OnDragField(
                    23,
                    (outlineSize.width * 0.36).toFloat(),
                    (outlineSize.height * 0.7775 + outlineYOffset).toFloat(),
                    160f
                ),
                //Top view, right back paw
                OnDragField(
                    24,
                    (outlineSize.width * 0.405).toFloat(),
                    (outlineSize.height * 0.85 + outlineYOffset).toFloat(),
                    125f
                ),
                //Top view, upper back
                OnDragField(
                    25,
                    (outlineSize.width * 0.265).toFloat(),
                    (outlineSize.height * 0.33 + outlineYOffset).toFloat(),
                    0f
                ),
                //Top view, lower back
                OnDragField(
                    26,
                    (outlineSize.width * 0.27).toFloat(),
                    (outlineSize.height * 0.58 + outlineYOffset).toFloat(),
                    0f
                ),


                //##BOTTOM VIEW##
                //Bottom view, left front paw
                OnDragField(
                    27,
                    (outlineSize.width * (0.13 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.04 + outlineYOffset).toFloat(),
                    160f
                ),
                //Bottom view, left lower arm
                OnDragField(
                    28,
                    (outlineSize.width * (0.155 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.1425 + outlineYOffset).toFloat(),
                    160f
                ),
                //Bottom view, left upper arm
                OnDragField(
                    29,
                    (outlineSize.width * (0.21 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.175 + outlineYOffset).toFloat(),
                    90f
                ),

                OnDragField(
                    30,
                    (outlineSize.width * (0.19 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.2475 + outlineYOffset).toFloat(),
                    90f
                ),
                //Bottom view, left armpit to stomach
                OnDragField(
                    31,
                    (outlineSize.width * (0.245 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.22 + outlineYOffset).toFloat(),
                    30f
                ),
                //Bottom view, chin
                OnDragField(
                    32,
                    (outlineSize.width * (0.27 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.1 + outlineYOffset).toFloat(),
                    90f
                ),
                //Bottom view, mouth
                OnDragField(
                    33,
                    (outlineSize.width * (0.27 + outlineOffset)).toFloat(),
                    outlineYOffset,
                    0f
                ),
                //Bottom view, left side rips
                OnDragField(
                    34,
                    (outlineSize.width * (0.215 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.33 + outlineYOffset).toFloat(),
                    170f
                ),
                OnDragField(
                    35,
                    (outlineSize.width * (0.2375 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.445 + outlineYOffset).toFloat(),
                    170f
                ),
                //Bottom view, left upper leg
                OnDragField(
                    36,
                    (outlineSize.width * (0.21 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.705 + outlineYOffset).toFloat(),
                    135f
                ),
                //Bottom view, left lower leg
                OnDragField(
                    37,
                    (outlineSize.width * (0.19 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.775 + outlineYOffset).toFloat(),
                    15f
                ),
                //Bottom view, left back paw
                OnDragField(
                    38,
                    (outlineSize.width * (0.145 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.865 + outlineYOffset).toFloat(),
                    55f
                ),
                //Bottom view, stomach
                OnDragField(
                    39,
                    (outlineSize.width * (0.265 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.33 + outlineYOffset).toFloat(),
                    0f
                ),


                //Bottom view, right front paw
                OnDragField(
                    40,
                    (outlineSize.width * (0.41 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.04 + outlineYOffset).toFloat(),
                    20f
                ),
                //Bottom view, right lower arm
                OnDragField(
                    41,
                    (outlineSize.width * (0.382 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.1425 + outlineYOffset).toFloat(),
                    20f
                ),
                //Bottom view, right upper arm
                OnDragField(
                    42,
                    (outlineSize.width * (0.325 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.175 + outlineYOffset).toFloat(),
                    90f
                ),
                OnDragField(
                    43,
                    (outlineSize.width * (0.34 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.2475 + outlineYOffset).toFloat(),
                    90f
                ),
                //Bottom view, right armpit to stomach
                OnDragField(
                    44,
                    (outlineSize.width * (0.2925 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.22 + outlineYOffset).toFloat(),
                    150f
                ),
                //Bottom view, right side rips
                OnDragField(
                    45,
                    (outlineSize.width * (0.3175 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.33 + outlineYOffset).toFloat(),
                    10f
                ),
                OnDragField(
                    46,
                    (outlineSize.width * (0.30 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.445 + outlineYOffset).toFloat(),
                    10f
                ),
                //Bottom view, right upper leg
                OnDragField(
                    47,
                    (outlineSize.width * (0.335 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.70 + outlineYOffset).toFloat(),
                    45f
                ),
                //Bottom view, right lower leg
                OnDragField(
                    48,
                    (outlineSize.width * (0.36 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.765 + outlineYOffset).toFloat(),
                    160f
                ),
                //Bottom view, right back paw
                OnDragField(
                    49,
                    (outlineSize.width * (0.405 + outlineOffset)).toFloat(),
                    (outlineSize.height * 0.85 + outlineYOffset).toFloat(),
                    125f
                )
            )

            Box(Modifier
                .offset {
                    IntOffset(
                        (canvasSize.width * 0.93).toInt(),
                        (canvasSize.height * 0.15).toInt()
                    )
                }
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
                .height(toolbarHeight.dp)
                .width(toolbarWidth.dp)
                .background(color = toolbarColor, shape = RoundedCornerShape(20.dp))


            )

            if (selectedPatient != null) {
                if (selectedPatient!!.bodyMaps.isNotEmpty()) {
                    LaunchedEffect(true)
                    {
                        for (i in selectedPatient!!.bodyMaps[selectedPatient!!.bodyMaps.lastIndex].bodyMapFieldValues.indices) {

                            for (field in fieldList) {
                                if (field.id == selectedPatient!!.bodyMaps[selectedPatient!!.bodyMaps.lastIndex].bodyMapFieldValues[i].id.toInt()) {

                                    field.initializeField(selectedPatient!!.bodyMaps[selectedPatient!!.bodyMaps.lastIndex].bodyMapFieldValues[i])

                                    break
                                }
                            }
                        }
                    }
                }
            }

            for (field in fieldList) {
                field.TransparentBox()
            }

            DraggableObjectPain(fieldList, canvasSize)
            DraggableObjectTension(fieldList, canvasSize)
            DraggableObjectResetField(fieldList, canvasSize)
            DraggableObjectCaptureField(canvasSize)
            PostBodyMapButton(fieldList, canvasSize)

            for (field in fieldList) {
                field.ColorSliderPain()
                field.ColorSliderTension()
            }
        }
    }
}