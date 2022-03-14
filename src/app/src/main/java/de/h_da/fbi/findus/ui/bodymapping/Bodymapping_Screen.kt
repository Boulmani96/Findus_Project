package de.h_da.fbi.findus.ui.bodymapping


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.h_da.fbi.findus.ui.Theme


/**
 * Bodymapping main screen
 */
@Composable
fun BodymappingScreen() {

    Theme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Bodymapping") })
            }
            //hier Code einfügen Screen bodymap
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Layout_Bodymapping()
            }
        }
    }
}