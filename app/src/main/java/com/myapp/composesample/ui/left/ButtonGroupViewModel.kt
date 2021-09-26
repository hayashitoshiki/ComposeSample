package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ロジック関連画面 UIロジック
 *
 */
@HiltViewModel
class ButtonGroupViewModel @Inject constructor() :
    BaseViewModel<ButtonContract.State, ButtonContract.Effect, ButtonContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): ButtonContract.State {
        return ButtonContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: ButtonContract.Event) {
        when (event) {
            is ButtonContract.Event.ChangeRadioButton -> changeRadioButton(event.value)
            is ButtonContract.Event.ChangeSlider -> changeSlide(event.value)
            is ButtonContract.Event.ChangeSwitch -> changeSwitch(event.value)
        }
    }

    /**
     * ラジオボタン変更
     *
     * @param value 選択したボタン
     */
    private fun changeRadioButton(value: Int) {
        setState { copy(checkedRadioButton = value) }
    }

    /**
     * スライダー変更
     *
     * @param value 変更する値
     */
    private fun changeSlide(value: Float) {
        setState { copy(sliderValue = value) }
    }

    /**
     * スイッチ変更
     *
     * @param value 変更する値
     */
    private fun changeSwitch(value: Boolean) {
        setState { copy(switchValue = value) }
    }

}
