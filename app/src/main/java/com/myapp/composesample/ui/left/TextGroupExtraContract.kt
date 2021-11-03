package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

/**
 * テキスト関連発展画面 状態、イベント、エフェクト定義
 *
 */
interface TextGroupExtraContract : BaseContract {

    /**
     * 状態保持
     *
     * @property text1 １つ目のテキスト入力欄
     * @property text2 ２つ目のテキスト入力欄
     * @property text3 ３つ目のテキスト入力欄
     * @property errorMessageText2 ２つ目の入力欄のエラー文言
     */
    data class State(
        val text1: String = "",
        val text2: String = "",
        val text3: String = "",
        val errorMessageText2: String = ""
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        object FocusChangeInput1ToInput2 : Effect()
        object FocusChangeInput2ToInput3 : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * １つ目の入力欄のテキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class ChangeText1(val text: String) : Event()

        /**
         * ２つ目の入力欄のテキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class ChangeText2(val text: String) : Event()

        /**
         * ３つ目の入力欄のテキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class ChangeText3(val text: String) : Event()
    }
}