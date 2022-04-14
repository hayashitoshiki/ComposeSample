package com.myapp.composesample.ui.center

import com.myapp.composesample.util.base.BaseContract

interface ListContract : BaseContract {

    /**
     * 状態保持
     *
     * @property isRefreshing リフレッシュ
     */
    data class State(
        val isRefreshing: Boolean = false,
        val sampleContent: List<String> = listOf("1", "2", "3", "4", "5")
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
        object Refresh : Event()
        data class MoveList(val from: Int, val to: Int) : Event()
    }
}