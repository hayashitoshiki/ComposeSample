package com.myapp.composesample.util.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat


/**
 * チャート用のValue Object
 *
 * @property label ラベル
 * @property value データ値
 * @property color データの色
 */
data class ChartValue(
    val label: String,
    val value: Float,
    val color: Color
)

/**
 * ドーナッツ型円グラフ + 中央ラベルありコンポーネント
 *
 * @param modifier レイアウト
 * @param items データリスト
 * @param circleLabel 渡したデータのラベル
 */
@Composable
fun DonutsChartContent(
    modifier: Modifier = Modifier,
    items: List<ChartValue>,
    circleLabel: String
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Box(Modifier.padding(16.dp)) {
            val proportions = items.extractProportions { it.value }
            val circleColors = items.map { it.color }
            val amountsTotal = items.map { it.value }.sum()

            // グラフ出力
            AnimatedCircleGraph(
                proportions,
                circleColors,
                Modifier
                    .height(300.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth()
            )

            // 中央テキスト
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = circleLabel,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = formatAmount(amountsTotal),
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(Modifier.height(10.dp))

        // ラベル出力
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                items.forEach { item ->
                    Text(text = item.label + " = " + item.value + "円")
                }
            }
        }
    }
}


/**
 * 合計値フォーマット
 *
 * @param amount 合計値
 * @return [#,###.##]区切りの合計値
 */
fun formatAmount(amount: Float): String {
    return AmountDecimalFormat.format(amount)
}

private val AmountDecimalFormat = DecimalFormat("#,###.##")

/**
 * 割合出力
 */
fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}

