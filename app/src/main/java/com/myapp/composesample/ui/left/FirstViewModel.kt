package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel

/**
 * ロジック関連画面 UIロジック
 *
 */
class FirstViewModel :
    BaseViewModel<FirstContract.State, FirstContract.Effect, FirstContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): FirstContract.State {
        return FirstContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: FirstContract.Event) {
        when (event) {
            is FirstContract.Event.NavigateToButtonGroupScreen -> setEffect { FirstContract.Effect.NavigateToButtonGroupScreen }
            is FirstContract.Event.NavigateToTextGroupScreen -> setEffect { FirstContract.Effect.NavigateToTextGroupScreen }
            is FirstContract.Event.NavigateToLogicScreen -> setEffect { FirstContract.Effect.NavigateToLogicScreen }
        }
    }

}
