package com.myapp.composesample.ui.center

import com.myapp.composesample.util.base.BaseContract

/**
 * チャートサンプル画面 UI状態管理
 *
 */
interface ChartContract : BaseContract {

    /**
     * 状態保持
     *
     */
    class State : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event
}