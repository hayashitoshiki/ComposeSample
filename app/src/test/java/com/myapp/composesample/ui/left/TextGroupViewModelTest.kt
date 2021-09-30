package com.myapp.composesample.ui.left

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 *  テキスト関連画面 ロジック仕様
 *
 */
class TextGroupViewModelTest {

    private val state = TextContract.State()

    private lateinit var  viewModel: TextGroupViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        viewModel = TextGroupViewModel()
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
    private fun result(state: TextContract.State, effect: TextContract.Effect?) {
        val resultState = viewModel.state.value
        var resultEffect: TextContract.Effect? = null
        viewModel.effect
            .onEach { resultEffect = it }
            .launchIn(testScope)
        // 比較
        Assert.assertEquals(state, resultState)
        Assert.assertEquals(effect, resultEffect)
    }

    // region １つ目の入力値変更

    /**
     * １つ目の入力欄変更
     *
     * 条件：なし
     * 期待結果：１つ目の入力欄設定値が変更されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeText1() {
        // 定数
        val value = "test test"
        // 期待結果
        val expectationsState = state.copy(text1 = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(TextContract.Event.ChangeText1(value))
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region ２つ目の入力値変更

    /**
     * ２つ目の入力欄変更
     *
     * 条件：なし
     * 期待結果：２つ目の入力欄設定値が変更されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeText2() {
        // 定数
        val value = "hello world"
        // 期待結果
        val expectationsState = state.copy(text2 = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(TextContract.Event.ChangeText2(value))
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region ３つ目の入力値変更

    /**
     * ３つ目の入力欄変更
     *
     * 条件：なし
     * 期待結果：３つ目の入力欄設定値が変更されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeText3() {
        // 定数
        val value = "こんにちは"
        // 期待結果
        val expectationsState = state.copy(text3 = value, textResult = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(TextContract.Event.ChangeText3(value))
        // 検証
        result(expectationsState, expectationsEffect)
    }

     // endregion

}