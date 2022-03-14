package de.h_da.fbi.findus.ui.bodymapping


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import de.h_da.fbi.findus.ui.bodyMapCaptureView


/**
 * Sets the layout for the bodymapping screen
 */
@Composable
fun Layout_Bodymapping() {

    Row(modifier = Modifier.background(color = MaterialTheme.colors.secondary)) {
        Column(modifier = Modifier.fillMaxWidth(0.25f)) {
            Img_profile()
            Spacer(Modifier.width(20.dp))
            Txt_name()
            Spacer(Modifier.height(5.dp))
            Txt_age()
            Spacer(Modifier.height(20.dp))
            List_Scrollview()
        }
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .background(shape = RoundedCornerShape(20.dp), color = Color.White)) {
            val context = LocalContext.current
            bodyMapCaptureView = remember { mutableStateOf(BodyMapping_View(context)) }

            AndroidView(modifier = Modifier.wrapContentSize(),
                factory = {
                    BodyMapping_View(context).apply {
                        post {
                            bodyMapCaptureView?.value = this
                        }
                    }
                }
            )
        }

        }
    }
