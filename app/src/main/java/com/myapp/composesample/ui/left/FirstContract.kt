package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

interface FirstContract : BaseContract {

    /**
     * 状態保持
     */
    class State : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        object NavigateToTextGroupScreen : Effect()
        object NavigateToButtonGroupScreen : Effect()
        object NavigateToLogicScreen : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        object NavigateToTextGroupScreen : Event()
        object NavigateToButtonGroupScreen : Event()
        object NavigateToLogicScreen : Event()
    }
}