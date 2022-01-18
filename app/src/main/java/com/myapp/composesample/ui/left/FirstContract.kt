package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

interface FirstContract : BaseContract {

    /**
     * 状態保持
     *
     * @property isViewState 状態保持(false = なし)
     */
    data class State(val isViewState: Boolean = false) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        object NavigateToTextGroupScreen : Effect()
        object NavigateToTextGroupExtraScreen : Effect()
        object NavigateToTextScrollerScreen : Effect()
        object NavigateToButtonGroupScreen : Effect()
        object NavigateToLogicScreen : Effect()
        object NavigateToListGroupScreen : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        object NavigateToTextGroupScreen : Event()
        object NavigateToTextGroupExtraScreen : Event()
        object NavigateToTextScrollerScreen : Event()
        object NavigateToButtonGroupScreen : Event()
        object NavigateToLogicScreen : Event()
        object NavigateToListGroupScreen : Event()
    }
}