package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

/**
 * ロジック関連画 状態、イベント、エフェクト定義
 *
 */
interface LogicContract : BaseContract {

    /**
     * 状態保持
     *
     * @property initCounter　初期表示カウンター
     * @property buttonCounter ボタンカウンター
     * @property text テキスト
     */
    data class State(
        val initCounter: Int = 0,
        val buttonCounter: Int = 0,
        val text: String = "",
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
         * 画面描画
         */
        object CreatedView : Event()

        /**
         * 画面削除
         */
        object Disposable : Event()

        /**
         * カウントアップボタン押下
         */
        object CountUp : Event()

        /**
         * カウントダウンボタン押下
         */
        object CountDown : Event()

        /**
         * テキスト変更
         *
         * @property text 変更後のテキスト
         */
        data class OnChangeText(val text: String) : Event()
    }
}