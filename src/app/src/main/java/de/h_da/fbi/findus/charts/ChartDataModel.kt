package de.h_da.fbi.findus.charts

import androidx.lifecycle.ViewModel


class ChartDataModel : ViewModel(){
    val chartDataList = mutableListOf(Pair(1f,"2002"),Pair(2f,"2003"),Pair(3f,"2004"),Pair(4f,"2005"),Pair(5f,"2006"),Pair(6f,"2007"),Pair(8f,"2010"))
}