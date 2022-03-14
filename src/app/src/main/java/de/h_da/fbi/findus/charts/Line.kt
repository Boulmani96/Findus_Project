package de.h_da.fbi.findus.charts

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

private val BIG_PADDING = 20.dp
private val WIDTH_VALUE = 450.dp
private val HEIGHT_VALUE = 450.dp
private const val MIN_SCALE = 0.5f
private const val MAX_SCALE = 3f
private const val HORIZONTAL_OFFSET = 5f
private var SCALE = 1f

/**
 * The List in the parameter will be animate as a line chart after converting it to a line chart data
 * @param randomList: List<Pair<Float,String>> a list from a pair Float and String what represent a data and the key
 * @see listConvertToLineCharData function
 */
@Composable
fun LineChartAnimation(randomList:List<Pair<Float,String>>) {
    var scale by remember { mutableStateOf(SCALE) }
    LineChart(
        lineChartData = LineChartData(points = listConvertToLineCharData(randomList)),
        // Optional properties.
        modifier = Modifier
            .width(WIDTH_VALUE)
            .height(HEIGHT_VALUE)
            .padding(BIG_PADDING)
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
        pointDrawer = FilledCircularPointDrawer(),
        lineDrawer = SolidLineDrawer(),
        xAxisDrawer = SimpleXAxisDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(),
        horizontalOffset = HORIZONTAL_OFFSET,
    )
}

/**
 * The List will be convert to a Line Chart Data
 * @param randomList: List<Pair<Float,String>> a list of a pair Float and String what represent a data and the key
 * @return Line Chart List
 */
@Composable
private fun listConvertToLineCharData(randomList: List<Pair<Float,String>>): List<LineChartData.Point> {
    val randomLineList: MutableList<LineChartData.Point> = mutableListOf()
    randomList.forEach {
        randomLineList += listOf(LineChartData.Point(it.first, it.second))
    }
    return randomLineList
}

@Preview
@Composable
private fun LineChartDemo(){
    val dataList = listOf(Pair(1f,"2002"),Pair(2f,"2003"),Pair(3f,"2004"),Pair(4f,"2005"),Pair(5f,"2006"),Pair(6f,"2007"),Pair(7f,"2008"))
    LineChartAnimation(dataList)
}