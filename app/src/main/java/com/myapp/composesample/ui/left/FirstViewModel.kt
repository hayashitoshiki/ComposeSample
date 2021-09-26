package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ロジック関連画面 UIロジック
 *
 */
@HiltViewModel
class FirstViewModel @Inject constructor() :
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
