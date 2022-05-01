package com.myapp.composesample.model.value

/**
 * STEP'Nのコインオブジェクト定義
 *
 */
sealed class StepnCoin {
    /**
     * 価格
     */
    abstract val money: Float

    /**
     * 表示用ラベル
     */
    abstract val label: String

    data class Gmt(override val money: Float) : StepnCoin(){
        override val label = "GMT"
    }
    data class Gst(override val money: Float) : StepnCoin(){
        override val label = "GST"
    }
    data class Solana(override val money: Float) : StepnCoin(){
        override val label = "SOLANA"
    }
}