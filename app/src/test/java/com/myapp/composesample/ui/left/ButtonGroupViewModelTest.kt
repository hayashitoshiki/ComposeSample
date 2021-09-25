package com.myapp.composesample.ui.left

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test


class ButtonGroupViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: ButtonGroupViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        viewModel = ButtonGroupViewModel()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region ラジオボタン変更

    /**
     * ラジオボタン変更
     *
     * 条件　　：ラジオボタンの数の分だけループ
     * 期待結果：渡された引数が設定されること
     */
    @Test
    fun changeRadioButton() {
        val state = ButtonContract.State()

        // UIに設置されている分だけループ
        for (i in 1..5) {
            viewModel.setEvent(ButtonContract.Event.ChangeRadioButton(i))
            val expectations = state.copy(checkedRadioButton = i)
            val result = viewModel.state.value
            assertEquals(expectations, result)
        }
    }

    // endregion

    // region スライダー変更

    /**
     * スライダー変更
     *
     * 条件：なし
     * 期待結果：引数で渡された値がスライダー値に設定されること
     *
     */
    @Test
    fun changeSlide() {
        val state = ButtonContract.State()
        val value = 50f
        viewModel.setEvent(ButtonContract.Event.ChangeSlider(value))
        val expectations = state.copy(sliderValue = value)
        val result = viewModel.state.value
        assertEquals(expectations, result)
    }

    // endregion

    // region スイッチ変更

    /**
     * スイッチ変更
     *
     * 条件：なし
     * 期待結果：引数で渡された値がスイッチ制御値に設定されること
     *
     */
    @Test
    fun changeSwitch() {
        // OFF
        val state = ButtonContract.State()
        val value1 = false
        viewModel.setEvent(ButtonContract.Event.ChangeSwitch(value1))
        var expectations = state.copy(switchValue = value1)
        var result = viewModel.state.value
        assertEquals(expectations, result)

        // ON
        val value2 = true
        viewModel.setEvent(ButtonContract.Event.ChangeSwitch(value2))
        expectations = state.copy(switchValue = value2)
        result = viewModel.state.value
        assertEquals(expectations, result)
    }

    // endregion

}