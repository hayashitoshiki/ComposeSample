package com.myapp.composesample.util

import android.content.Context


/**
 * Px -> Dp 変換
 *
 * @param context コンテキスト
 * @param px px値
 * @return dp値
 */
fun converterPxToTo(context: Context, px: Float): Float {
    return px / context.resources.displayMetrics.density
}