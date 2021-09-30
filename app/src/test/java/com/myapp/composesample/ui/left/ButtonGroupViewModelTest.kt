package com.myapp.composesample.ui.left

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 *  Button関連画面 ロジック仕様
 *
 */
class ButtonGroupViewModelTest {

    private val state = ButtonContract.State()

    private lateinit var viewModel: ButtonGroupViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

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

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(state: ButtonContract.State, effect: ButtonContract.Effect?) {
        val resultState = viewModel.state.value
        var resultEffect: ButtonContract.Effect? = null
        viewModel.effect
            .onEach { resultEffect = it }
            .launchIn(testScope)
        // 比較
        assertEquals(state, resultState)
        assertEquals(effect, resultEffect)
    }

    // region ラジオボタン変更

    /**
     * ラジオボタン変更
     *
     * 条件　　：ラジオボタンの数の分だけループ
     * 期待結果：渡された引数が設定されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeRadioButton() {
        // UIに設置されている分だけループ
        for (i in 1..5) {
            // 期待結果
            val expectationsState = state.copy(checkedRadioButton = i)
            val expectationsEffect = null
            // 実行
            viewModel.setEvent(ButtonContract.Event.ChangeRadioButton(i))
            // 検証
            result(expectationsState, expectationsEffect)
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
    @ExperimentalCoroutinesApi
    @Test
    fun changeSlide() {
        // 定数
        val value = 50f
        // 期待結果
        val expectationsState = state.copy(sliderValue = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(ButtonContract.Event.ChangeSlider(value))
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region スイッチ変更

    /**
     * スイッチ変更
     *
     * 条件：OFFにする
     * 期待結果：引数で渡された値がスイッチ制御値にfalseが設定されること
     *
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeSwitchByOff() {
        // 定数
        val value1 = false
        // 期待結果
        val expectationsState = state.copy(switchValue = value1)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(ButtonContract.Event.ChangeSwitch(value1))
        // 検証
        result(expectationsState, expectationsEffect)
    }
    /**
     * スイッチ変更
     *
     * 条件：ON(true)にする
     * 期待結果：引数で渡された値がスイッチ制御値にtrueが設定されること
     *
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeSwitchByOn() {
        // 定数
        val value = true
        // 期待結果
        val expectationsState = state.copy(switchValue = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(ButtonContract.Event.ChangeSwitch(value))
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

}