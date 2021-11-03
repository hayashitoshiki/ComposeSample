package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

/**
 * リスト関連発展画面 状態、イベント、エフェクト定義
 *
 */
interface ListGroupContract : BaseContract {

    /**
     * 状態保持
     *
     * @property textList 文字列リスト
     * @property isEnableAddTextButton 文字列リスト追加ボタン活性制御
     * @property isEnableDeleteTextButton 文字列リスト削除ボタン活性制御
     */
    data class State(
        val textList: List<String> = listOf(),
        val isEnableAddTextButton: Boolean = true,
        val isEnableDeleteTextButton: Boolean = false,
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
         * 文字列リスト項目追加
         *
         */
        object AddTextList : Event()

        /**
         * 文字列リスト項目削除
         *
         */
        object DeleteTextList : Event()
    }
}