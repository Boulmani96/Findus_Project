package de.h_da.fbi.findus.ui.bodymapping

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.h_da.fbi.common.entity.ApiBodyMapCreateUpdate
import de.h_da.fbi.common.entity.ApiFieldValues
import de.h_da.fbi.common.repository.FindusRepository
import de.h_da.fbi.findus.R
import de.h_da.fbi.findus.ui.bodyMapCaptureView
import de.h_da.fbi.findus.ui.patientsList
import de.h_da.fbi.findus.ui.selectedPatient
import kotlinx.coroutines.launch
import kotlin.math.abs


/**
 * Creates a draggable object used to set the pain attribute on an OnDragField
 * @param fieldList List of OnDragFields
 * @param canvasSize Size of the canvas
 */
@Composable
fun DraggableObjectPain(fieldList: List<OnDragField>, canvasSize: Size) {
    val BOX_SIZE = 35
    val OFFSET_X = (canvasSize.width * 0.936).toInt()
    val OFFSET_Y = (canvasSize.height * 0.2).toInt()
    val TOLERANCE = 27


    val sector1: MutableList<OnDragField> = ArrayList()
    val sector2: MutableList<OnDragField> = ArrayList()
    val sector3: MutableList<OnDragField> = ArrayList()
    val sector4: MutableList<OnDragField> = ArrayList()
    val sector5: MutableList<OnDragField> = ArrayList()
    val sector6: MutableList<OnDragField> = ArrayList()
    val sector7: MutableList<OnDragField> = ArrayList()
    val sector8: MutableList<OnDragField> = ArrayList()
    val sector9: MutableList<OnDragField> = ArrayList()

    for (field in fieldList) {
        //first row
        if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector1.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector2.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector3.add(field)
        }
        //second row
        else if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector4.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector5.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector6.add(field)
        }
        //third row
        else if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector7.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector8.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector9.add(field)
        }

    }


    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(OFFSET_X) }
        var offsetY by remember { mutableStateOf(OFFSET_Y) }

        val scope = rememberCoroutineScope()

        Box(
            Modifier
                .offset { IntOffset(offsetX, offsetY) }
                .shadow(elevation = 5.dp, shape = CircleShape)
                .background(shape = CircleShape, color = Color(0xfFED4B4B))
                .border(2.dp, Color(0xFF800000), CircleShape)
                .size(BOX_SIZE.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            offsetX += dragAmount.x.toInt()
                            offsetY += dragAmount.y.toInt()
                            scope.launch {

                                //first sector
                                if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector1) {
                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }

                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //second sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector2) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )

                                    }
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //third sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector3) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //fourth sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {

                                    for (coordinate in sector4) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //fifth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector5) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //sixth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector6) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //seventh sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector7) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //eighth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector8) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector1)
                                    deactivateOtherSectorPain(sector9)
                                }
                                //ninth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector9) {

                                        highlightFieldPain(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorPain(sector2)
                                    deactivateOtherSectorPain(sector3)
                                    deactivateOtherSectorPain(sector4)
                                    deactivateOtherSectorPain(sector5)
                                    deactivateOtherSectorPain(sector6)
                                    deactivateOtherSectorPain(sector7)
                                    deactivateOtherSectorPain(sector8)
                                    deactivateOtherSectorPain(sector1)
                                }

                            }
                        },
                        onDragEnd = {
                            scope.launch {

                                //first sector
                                if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector1) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //second sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector2) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //third sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector3) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //fourth sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector4) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //fifth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector5) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //sixth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector6) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //seventh sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector7) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //eigth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector8) {

                                        fillFieldPain(coordinate)
                                    }
                                }
                                //ninth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector9) {

                                        fillFieldPain(coordinate)
                                    }
                                }

                                offsetX = OFFSET_X
                                offsetY = OFFSET_Y
                            }
                        })
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.bodymap_draggable_pain),
                contentDescription = null,
                Modifier.size(dimensionResource(id = R.dimen.size_bodymap_draggable_pain)),
                tint = Color.Black
            )

        }
    }


}

/**
 * Creates a draggable object used to set the tension attribute on an OnDragField
 * @param fieldList List of OnDragFields
 * @param canvasSize Size of the canvas
 */
@Composable
fun DraggableObjectTension(fieldList: List<OnDragField>, canvasSize: Size) {


    val BOX_SIZE = 35
    val OFFSET_X = (canvasSize.width * 0.936).toInt()
    val OFFSET_Y = (((canvasSize.height * 0.2) * 1.5).toInt())
    val TOLERANCE = 50

    val sector1: MutableList<OnDragField> = ArrayList()
    val sector2: MutableList<OnDragField> = ArrayList()
    val sector3: MutableList<OnDragField> = ArrayList()
    val sector4: MutableList<OnDragField> = ArrayList()
    val sector5: MutableList<OnDragField> = ArrayList()
    val sector6: MutableList<OnDragField> = ArrayList()
    val sector7: MutableList<OnDragField> = ArrayList()
    val sector8: MutableList<OnDragField> = ArrayList()
    val sector9: MutableList<OnDragField> = ArrayList()

    for (field in fieldList) {
        //first row
        if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector1.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector2.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector3.add(field)
        }
        //second row
        else if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector4.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector5.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector6.add(field)
        }
        //third row
        else if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector7.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector8.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector9.add(field)
        }

    }





    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(OFFSET_X) }
        var offsetY by remember { mutableStateOf(OFFSET_Y) }

        val scope = rememberCoroutineScope()

        Box(
            Modifier
                .offset { IntOffset(offsetX, offsetY) }
                .shadow(elevation = 5.dp, shape = CircleShape)
                .background(shape = CircleShape, color = Color(0xFF2525FF))
                .border(2.dp, Color(0xFF6666FF), CircleShape)
                .size(BOX_SIZE.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            offsetX += dragAmount.x.toInt()
                            offsetY += dragAmount.y.toInt()
                            scope.launch {

                                //first sector
                                if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector1) {
                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }

                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //second sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector2) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )

                                    }
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //third sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector3) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //fourth sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {

                                    for (coordinate in sector4) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //fifth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector5) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //sixth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector6) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //seventh sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector7) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //eigth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector8) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector1)
                                    deactivateOtherSectorTension(sector9)
                                }
                                //ninth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector9) {

                                        highlightFieldTension(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorTension(sector2)
                                    deactivateOtherSectorTension(sector3)
                                    deactivateOtherSectorTension(sector4)
                                    deactivateOtherSectorTension(sector5)
                                    deactivateOtherSectorTension(sector6)
                                    deactivateOtherSectorTension(sector7)
                                    deactivateOtherSectorTension(sector8)
                                    deactivateOtherSectorTension(sector1)
                                }


                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                //first sector
                                if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector1) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //second sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector2) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //third sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector3) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //fourth sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector4) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //fifth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector5) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //sixth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector6) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //seventh sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector7) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //eigth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector8) {

                                        fillFieldTension(coordinate)
                                    }
                                }
                                //ninth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector9) {

                                        fillFieldTension(coordinate)
                                    }
                                }

                                offsetX = OFFSET_X
                                offsetY = OFFSET_Y
                            }
                        })
                },
            contentAlignment = Alignment.Center
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.bodymap_draggable_tension),
                contentDescription = null,
                Modifier.size(dimensionResource(id = R.dimen.size_bodymap_draggable_tension)),
                tint = Color.White
            )
        }
    }


}

/**
 * Creates a draggable object used to reset the attributes of an OnDragField
 * @param fieldList List of OnDragFields
 * @param canvasSize Size of the canvas
 */
@Composable
fun DraggableObjectResetField(fieldList: List<OnDragField>, canvasSize: Size) {


    val BOX_SIZE = 35
    val OFFSET_X = (canvasSize.width * 0.936).toInt()
    val OFFSET_Y = (((canvasSize.height * 0.2) * 2).toInt())
    val TOLERANCE = 50

    val sector1: MutableList<OnDragField> = ArrayList()
    val sector2: MutableList<OnDragField> = ArrayList()
    val sector3: MutableList<OnDragField> = ArrayList()
    val sector4: MutableList<OnDragField> = ArrayList()
    val sector5: MutableList<OnDragField> = ArrayList()
    val sector6: MutableList<OnDragField> = ArrayList()
    val sector7: MutableList<OnDragField> = ArrayList()
    val sector8: MutableList<OnDragField> = ArrayList()
    val sector9: MutableList<OnDragField> = ArrayList()

    for (field in fieldList) {
        //first row
        if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector1.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector2.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height * 1 / 3)) {
            sector3.add(field)
        }
        //second row
        else if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector4.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector5.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height * 2 / 3) && field.posY > (canvasSize.height * 1 / 3)) {
            sector6.add(field)
        }
        //third row
        else if (field.posX <= (canvasSize.width * 1 / 3) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector7.add(field)
        } else if (field.posX > (canvasSize.width * 1 / 3) && field.posX <= (canvasSize.width * 2 / 3) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector8.add(field)
        } else if (field.posX > (canvasSize.width * 2 / 3) && field.posX <= (canvasSize.width) && field.posY <= (canvasSize.height) && field.posY > (canvasSize.height * 2 / 3)) {
            sector9.add(field)
        }

    }

    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(OFFSET_X) }
        var offsetY by remember { mutableStateOf(OFFSET_Y) }

        val scope = rememberCoroutineScope()

        Box(
            Modifier
                .offset { IntOffset(offsetX, offsetY) }
                .shadow(elevation = 5.dp, shape = CircleShape)
                .background(shape = CircleShape, color = Color.White)
                .border(2.dp, Color.Gray, CircleShape)
                .size(BOX_SIZE.dp)
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consumeAllChanges()
                            offsetX += dragAmount.x.toInt()
                            offsetY += dragAmount.y.toInt()
                            scope.launch {

                                //first sector
                                if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector1) {
                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }

                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //second sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector2) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )

                                    }
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //third sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector3) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //fourth sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {

                                    for (coordinate in sector4) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //fifth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector5) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //sixth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector6) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //seventh sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector7) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //eigth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector8) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector1)
                                    deactivateOtherSectorDelete(sector9)
                                }
                                //ninth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector9) {

                                        highlightFieldDelete(
                                            coordinate,
                                            offsetX,
                                            offsetY,
                                            BOX_SIZE,
                                            TOLERANCE
                                        )
                                    }
                                    deactivateOtherSectorDelete(sector2)
                                    deactivateOtherSectorDelete(sector3)
                                    deactivateOtherSectorDelete(sector4)
                                    deactivateOtherSectorDelete(sector5)
                                    deactivateOtherSectorDelete(sector6)
                                    deactivateOtherSectorDelete(sector7)
                                    deactivateOtherSectorDelete(sector8)
                                    deactivateOtherSectorDelete(sector1)
                                }


                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                //first sector
                                if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector1) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //second sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector2) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //third sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector3) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //fourth sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector4) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //fifth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector5) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //sixth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height * 2 / 3) && offsetY > (canvasSize.height * 1 / 3)) {
                                    for (coordinate in sector6) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //seventh sector
                                else if (offsetX <= (canvasSize.width * 1 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector7) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //eigth sector
                                else if (offsetX > (canvasSize.width * 1 / 3) && offsetX <= (canvasSize.width * 2 / 3) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector8) {

                                        fillFieldDelete(coordinate)
                                    }
                                }
                                //ninth sector
                                else if (offsetX > (canvasSize.width * 2 / 3) && offsetX <= (canvasSize.width) && offsetY <= (canvasSize.height) && offsetY > (canvasSize.height * 2 / 3)) {
                                    for (coordinate in sector9) {

                                        fillFieldDelete(coordinate)
                                    }
                                }

                                offsetX = OFFSET_X
                                offsetY = OFFSET_Y
                            }
                        })
                },
            contentAlignment = Alignment.Center
        )
        {

            Icon(
                tint = Color.Black,
                imageVector = Icons.Filled.Delete,
                contentDescription = null
            )
        }
    }


}

/**
 * Highlights a specific OnDragField if a draggable for pain is in its area of tolerance
 * @param coordinate Coordinate of a specific OnDragField
 * @param offsetX Relative X Offset
 * @param offsetY Relative Y Offset
 * @param BOX_SIZE Size of an OnDragField box
 * @param TOLERANCE Value used for the tolerance of how far away a draggable can be for an OnDragField to be registered
 */
suspend fun highlightFieldPain(
    coordinate: OnDragField,
    offsetX: Int,
    offsetY: Int,
    BOX_SIZE: Int,
    TOLERANCE: Int
) {
    if (abs((coordinate.posX + (coordinate.fieldwidth / 2)) - (offsetX + (BOX_SIZE / 2))) < TOLERANCE &&
        abs((coordinate.posY + (coordinate.fieldheight / 2)) - (offsetY + (BOX_SIZE / 2))) < TOLERANCE &&
        !coordinate.filledPain.value
    ) {
        coordinate.setColor(Color.Yellow)
        coordinate.setAlphaFull()
        coordinate.currentlyselected = true
    } else if (!coordinate.filledPain.value) {
        if (!coordinate.filledTension.value) {
            coordinate.setAlphaHalf()
        }
        coordinate.setSliderColor()
        coordinate.currentlyselected = false

    }
}

/**
 * Highlights a specific OnDragField if a draggable for tension is in its area of tolerance
 * @param coordinate Coordinate of a specific OnDragField
 * @param offsetX Relative X Offset
 * @param offsetY Relative Y Offset
 * @param BOX_SIZE Size of an OnDragField box
 * @param TOLERANCE Value used for the tolerance of how far away a draggable can be for an OnDragField to be registered
 */
suspend fun highlightFieldTension(
    coordinate: OnDragField,
    offsetX: Int,
    offsetY: Int,
    BOX_SIZE: Int,
    TOLERANCE: Int
) {

    if (abs((coordinate.posX + (coordinate.fieldwidth / 2)) - (offsetX + (BOX_SIZE / 2))) < TOLERANCE &&
        abs((coordinate.posY + (coordinate.fieldheight / 2)) - (offsetY + (BOX_SIZE / 2))) < TOLERANCE &&
        !coordinate.filledTension.value
    ) {
        coordinate.setColor(Color.Yellow)
        coordinate.setAlphaFull()
        coordinate.currentlyselected = true

    } else if (!coordinate.filledTension.value) {
        if (!coordinate.filledPain.value) {
            coordinate.setAlphaHalf()
        }
        coordinate.setSliderColor()
        coordinate.currentlyselected = false

    }
}

/**
 * Highlights a specific OnDragField if a draggable for resetting is in its area of tolerance
 * @param coordinate Coordinate of a specific OnDragField
 * @param offsetX Relative X Offset
 * @param offsetY Relative Y Offset
 * @param BOX_SIZE Size of an OnDragField box
 * @param TOLERANCE Value used for the tolerance of how far away a draggable can be for an OnDragField to be registered
 */
suspend fun highlightFieldDelete(
    coordinate: OnDragField,
    offsetX: Int,
    offsetY: Int,
    BOX_SIZE: Int,
    TOLERANCE: Int
) {

    if (abs((coordinate.posX + (coordinate.fieldwidth / 2)) - (offsetX + (BOX_SIZE / 2))) < TOLERANCE &&
        abs((coordinate.posY + (coordinate.fieldheight / 2)) - (offsetY + (BOX_SIZE / 2))) < TOLERANCE
        && (coordinate.filledPain.value || coordinate.filledTension.value)
    ) {
        coordinate.setColor(Color.Yellow)
        coordinate.setAlphaFull()
        coordinate.currentlyselected = true

    } else {
        if (!coordinate.filledPain.value && !coordinate.filledTension.value) {
            coordinate.setAlphaHalf()
        }
        coordinate.setSliderColor()
        coordinate.currentlyselected = false


    }
}

/**
 * Deactivates other sectors pain operation for efficiency
 * @param sector A specific sector of OnDragFields
 */
suspend fun deactivateOtherSectorPain(sector: MutableList<OnDragField>) {

    for (coordinate in sector) {
        if (!coordinate.filledPain.value) {
            if (!coordinate.filledTension.value) {
                coordinate.setAlphaHalf()
            }
            coordinate.setSliderColor()
            coordinate.currentlyselected = false
        }
    }

}

/**
 * Deactivates other sectors tension operation for efficiency
 * @param sector A specific sector of OnDragFields
 */
suspend fun deactivateOtherSectorTension(sector: MutableList<OnDragField>) {

    for (coordinate in sector) {
        if (!coordinate.filledTension.value) {
            if (!coordinate.filledPain.value) {
                coordinate.setAlphaHalf()
            }
            coordinate.setSliderColor()
            coordinate.currentlyselected = false

        }
    }

}

/**
 * Deactivates other sectors reset operation for efficiency
 * @param sector A specific sector of OnDragFields
 */
suspend fun deactivateOtherSectorDelete(sector: MutableList<OnDragField>) {

    for (coordinate in sector) {
        if (!coordinate.filledPain.value && !coordinate.filledTension.value) {
            coordinate.setAlphaHalf()
        }
        coordinate.setSliderColor()
        coordinate.currentlyselected = false
    }
}

/**
 * Fills an OnDragField according to pain
 * @param coordinate Coordinate of a specific OnDragField
 */
fun fillFieldPain(coordinate: OnDragField) {
    if (coordinate.currentlyselected) {
        coordinate.filledPain.value = true
        coordinate.currentlyselected = false
        coordinate.colorChangevisiblePain.value = true
    }
}

/**
 * Fills an OnDragField according to tension
 * @param coordinate Coordinate of a specific OnDragField
 */
fun fillFieldTension(coordinate: OnDragField) {
    if (coordinate.currentlyselected) {
        coordinate.filledTension.value = true
        coordinate.currentlyselected = false
        coordinate.colorChangevisibleTension.value = true
    }
}

/**
 * Fills an OnDragField according to a reset
 * @param coordinate Coordinate of a specific OnDragField
 */
suspend fun fillFieldDelete(coordinate: OnDragField) {

    if (coordinate.currentlyselected) {
        coordinate.filledTension.value = false
        coordinate.filledPain.value = false
        coordinate.currentlyselected = false
        coordinate.painintensity = 0
        coordinate.tensionintensity = 0
        coordinate.setAlphaHalf()
        coordinate.setSliderColor()
    }

}

/**
 * Creates a button used for downloading a bodymap image
 * @param canvasSize Size of the canvas
 */
@Composable
fun DraggableObjectCaptureField(canvasSize: Size) {

    val OFFSET_X = (canvasSize.width * 0.936).toInt()
    val OFFSET_Y = (((canvasSize.height * 0.2)) * 3).toInt()
    val BOX_SIZE = 35
    val context = LocalContext.current


    IconButton(
        modifier = Modifier
            .offset { IntOffset(OFFSET_X, OFFSET_Y) }
            .size(BOX_SIZE.dp),
        onClick = {
            downloadBodyMap(); Toast.makeText(
            context,
            "successfully downloaded bodyMap image",
            Toast.LENGTH_SHORT
        ).show()
        }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bodymap_download_logo),
            contentDescription = "Download",
            Modifier.size(dimensionResource(id = R.dimen.size_bodymap_download)),
            tint = MaterialTheme.colors.onSecondary
        )
    }


}

/**
 * Creates a button used for posting an edited bodymap
 * @param fieldList List of OnDragFields
 * @param canvasSize Size of the canvas
 */
@Composable
fun PostBodyMapButton(fieldList: List<OnDragField>, canvasSize: Size) {

    val scope = rememberCoroutineScope()
    val OFFSET_X = (canvasSize.width * 0.936).toInt()
    val OFFSET_Y = (((canvasSize.height * 0.2)) * 3.5).toInt()
    val BOX_SIZE = 35
    val context = LocalContext.current

    IconButton(
        modifier = Modifier
            .offset { IntOffset(OFFSET_X, OFFSET_Y) }
            .size(BOX_SIZE.dp),
        onClick = {
            if (selectedPatient != null) {
                scope.launch {
                    val postList: MutableList<ApiFieldValues> = ArrayList()
                    for (field in fieldList) {

                        if (field.filledPain.value || field.filledTension.value) {
                            postList.add(
                                ApiFieldValues(
                                    field.id.toString(),
                                    field.painintensity,
                                    field.tensionintensity
                                )
                            )
                        }
                        FindusRepository().postBodyMap(
                            selectedPatient?.id.toString(),
                            ApiBodyMapCreateUpdate(postList)
                        )
                        selectedPatient = FindusRepository().fetchPatient(selectedPatient!!.id)
                    }
                }
                Toast.makeText(
                    context,
                    "successfully saved BodyMap",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    ) {
        Icon(
            contentDescription = "Upload",
            imageVector = Icons.Filled.CheckCircle,
            tint = MaterialTheme.colors.onSecondary
        )
    }


}

/**
 * Creates a button used for capturing a bodymap
 */
fun downloadBodyMap() {
    bodyMapCaptureView?.value?.capture(
        bodyMapCaptureView?.value as BodyMapping_View
    )
}
