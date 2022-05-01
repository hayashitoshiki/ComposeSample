package com.myapp.composesample.ui.center

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.myapp.composesample.model.value.StepnCoin
import com.myapp.composesample.util.Extension.chartColor
import com.myapp.composesample.util.component.ChartValue
import com.myapp.composesample.util.component.DonutsChartContent


/**
 * グラフ画面
 *
 */
@Composable
fun ChartScreen(
    viewModel: ChartViewModel
) {

    ChartContent(viewModel)
}


@Composable
private fun ChartContent(
    viewModel: ChartViewModel
) {
    val data = listOf(
        StepnCoin.Gst(738f),
        StepnCoin.Gmt(501f),
        StepnCoin.Solana(12800f)
    )

    val chartValues = data.map{ ChartValue(it.label, it.money, it.chartColor()) }
    Column {
        DonutsChartContent(
            items = chartValues,
            circleLabel = "STEP'N COIN　合計値"
        )
    }

}