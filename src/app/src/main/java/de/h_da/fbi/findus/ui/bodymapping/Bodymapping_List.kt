package de.h_da.fbi.findus.ui.bodymapping

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.h_da.fbi.findus.R


/**
 * Creates a list of list items for patient photos
 */
@Composable
fun List_Scrollview() {
    val list = listItems
    LazyColumn {
        itemsIndexed(list) { index, item ->
            ListItem()
        }
    }
}

private val listItems: List<ListItem> = listOf(
    ListItem("Marc"),
    ListItem("Gil"),
    ListItem("Juice WRLD"),
    ListItem("Callan"),
    ListItem("Braxton"),
    ListItem("Kyla"),
    ListItem("Lil Mosey"),
    ListItem("Allan"),
    ListItem("Mike"),
    ListItem("Drew"),
    ListItem("Nia"),
    ListItem("Coi Relay")
)

/**
 * Creates a list item for one patient photo
 */
@Composable
fun ListItem() {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(0.5f)
            .fillMaxHeight(0.5f)
    ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_add_a_photo_24),
                contentDescription = "user icon",
                contentScale = ContentScale.Crop,
            )
    }
}