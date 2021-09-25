package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseContract

interface ButtonContract : BaseContract {

    /**
     * 状態保持
     *
     * @property checkedRadioButton
     * @property switchValue
     * @property sliderValue
     */
    data class State(
        val checkedRadioButton: Int = 0,
        val switchValue: Boolean = true,
        val sliderValue: Float = 50f
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
        data class ChangeRadioButton(val value: Int) : Event()
        data class ChangeSlider(val value: Float) : Event()
        data class ChangeSwitch(val  value: Boolean) : Event()
    }
}