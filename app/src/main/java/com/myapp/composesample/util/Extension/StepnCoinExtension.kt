package com.myapp.composesample.util.Extension

import androidx.compose.ui.graphics.Color
import com.myapp.composesample.model.value.StepnCoin


/**
 * StepnCoinの色定義
 *
 * @return 各Labelに対するグラフ色
 */
fun StepnCoin.chartColor() : Color {
    return when(this) {
        is StepnCoin.Gmt -> Color.Yellow
        is StepnCoin.Gst -> Color.Gray
        is StepnCoin.Solana -> Color.Blue
    }
}