package de.h_da.fbi.findus.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.github.tehras.charts.piechart.renderer.SimpleSliceDrawer

private val SMALL_PADDING = 10.dp
private val ROUNDED_CORNER_SHAPE = 8.dp
private val ELEVATION = 8.dp
private val WIDTH_VALUE = 280.dp
private val HEIGHT_VALUE = 250.dp
private const val SIMPLE_SLICE_DRAWER = 100f
private const val DRAW_CIRCLE_RADIUS = 30f
private const val MAX_NUMBER_OF_COLOR = 10
/**
 *  The colors will be used to animate a pie chart
 */
private var pieChartsColors = mutableListOf(
    Color(0XFF673AB7),
    Color(0XFF3F51B5),
    Color(0XFF03A9F4),
    Color(0XFF009688),
    Color(0XFFCDDC39),
    Color(0XFFFFC107),
    Color(0XFFFF5722),
    Color(0XFF795548),
    Color(0XFF9E9E9E),
    Color(0XFF607D8B)
)

/**
 * The List in the parameter will be animate as a pie chart after to convert it to a pie chart data
 * @param dataList: List<Pair<Float,String>> a list of a pair Float and String what represent a data and the key
 * @see convertToPieChartData function
 */
@Composable
fun PieChartAnimation(dataList:List<Pair<Float,String>>) {
    val pieRandomList = convertToPieChartData(dataList)
    Surface(
        color = LightGray,
        shape = RoundedCornerShape(ROUNDED_CORNER_SHAPE),
        elevation = ELEVATION){
    Row(modifier = Modifier.padding(SMALL_PADDING)) {
        PieChart(
            pieChartData = PieChartData(pieRandomList),
            // Optional properties.
            modifier = Modifier
                .width(WIDTH_VALUE)
                .height(HEIGHT_VALUE),
            animation = simpleChartAnimation(),
            sliceDrawer = SimpleSliceDrawer(SIMPLE_SLICE_DRAWER)
        )
        var dataListNext = 0
        Column {
            pieRandomList.forEach {
                    Row(modifier = Modifier.padding(SMALL_PADDING)) {
                        Canvas(modifier = Modifier.padding(SMALL_PADDING)) {
                            drawCircle(color = it.color, radius = DRAW_CIRCLE_RADIUS)
                        }
                        Spacer(modifier = Modifier.padding(SMALL_PADDING))
                        Text(text = dataList[dataListNext].second)
                        dataListNext++
                    }
                }
            }
        }
    }
}

var counter = 0
@Composable
fun randomColor():Color {
    counter ++
    return if (counter < MAX_NUMBER_OF_COLOR) {
        pieChartsColors[counter]
    } else  {
        counter = 0
        pieChartsColors[counter]
    }
}

/**
 * The List will be convert to a pie Chart Data
 * @param dataList: List<Pair<Float,String>> a list of a pair Float and String what represent a data and the key
 * @return Pie Chart List
 */
@Composable
private fun convertToPieChartData(dataList: List<Pair<Float,String>>): List<PieChartData.Slice> {
    val dataLineList: MutableList<PieChartData.Slice> = mutableListOf()
    dataList.forEach {
        dataLineList += listOf(PieChartData.Slice(it.first, randomColor()))
    }
    return dataLineList
}

@Preview
@Composable
private fun PieChartDemo(){
    val dataList = listOf(Pair(1f,"2002"),Pair(2f,"2003"),Pair(3f,"2004"),Pair(4f,"2005"),Pair(5f,"2006"),Pair(6f,"2007"))
    PieChartAnimation(dataList)
}