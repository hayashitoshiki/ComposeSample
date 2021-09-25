package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

/**
 * ロジック関連画 状態、イベント、エフェクト定義
 *
 */
interface TextContract : BaseContract {

    /**
     * 状態保持
     *
     * @property text1 入力欄テキスト入力文言
     * @property text2 OutLineテキスト入力欄文言
     * @property text3 結果反映テキスト入力欄文言
     * @property textResult 入力欄入力結果反映テキスト
     */
    data class State(
        val text1: String = "入力欄１",
        val text2: String = "入力欄２",
        val text3: String = "入力欄３",
        val textResult: String = "初めは文字が違うよ"
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * テキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class ChangeText1(val text: String) : Event()

        /**
         * テキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class ChangeText2(val text: String) : Event()

        /**
         * テキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class ChangeText3(val text: String) : Event()
    }
}