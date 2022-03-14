package de.h_da.fbi.findus.charts


import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

private val SMALL_PADDING = 20.dp
private const val MIN_SCALE = 0.5f
private const val MAX_SCALE = 3f
private var SCALE = 1f
/**
 * The List in the parameter will be animate as a bar chart after to convert it to a bar chart data
 * @param dataList: List<Pair<Float,String>> a list of a pair Float and String what represent a data and the key
 * @see listConvertToBarChartData function
 */
@Composable
fun BarChartAnimation(dataList:List<Pair<Float,String>>) {
    var scale by remember { mutableStateOf(SCALE) }
    BarChart(
        barChartData = BarChartData(bars = listConvertToBarChartData(dataList)),
        // Optional properties.
        modifier = Modifier
            .fillMaxSize()
            .padding(SMALL_PADDING)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            )
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ ->
                    scale = when {
                        scale < MIN_SCALE -> MIN_SCALE
                        scale > MAX_SCALE -> MAX_SCALE
                        else -> scale * zoom
                    }
                }
            },
        animation = simpleChartAnimation(),
        barDrawer = SimpleBarDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        labelDrawer = SimpleValueDrawer(SimpleValueDrawer.DrawLocation.XAxis)
    )
}

/**
 * The List will be convert to a bar Chart Data
 * @param dataList: List<Pair<Float,String>> a list of a pair Float and String what represent a data and the key
 * @return Bar Chart List
 */
@Composable
private fun listConvertToBarChartData(dataList: List<Pair<Float,String>>): List<BarChartData.Bar> {
    val dataBarList: MutableList<BarChartData.Bar> = mutableListOf()
    dataList.forEach {
        dataBarList += listOf(BarChartData.Bar(label = it.second, value = it.first, color = Color.Blue))
    }
    return dataBarList
}

@Preview
@Composable
private fun BarChartDemo(){
    val dataList = listOf(Pair(1f,"2002"),Pair(2f,"2003"),Pair(3f,"2004"),Pair(4f,"2005"),Pair(5f,"2006"),Pair(6f,"2007"),Pair(7f,"2008"))
    BarChartAnimation(dataList)
}

