package com.myapp.composesample.ui.left

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TextGroupViewModelTest {

    lateinit var  viewModel: TextGroupViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

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

    // region １つ目の入力値変更

    /**
     * １つ目の入力欄変更
     *
     * 条件：なし
     * 期待結果：１つ目の入力欄設定値が変更されること
     */
    @Test
    fun changeText1() {
        val state = TextContract.State()
        val value = "test test"
        viewModel.setEvent(TextContract.Event.ChangeText1(value))
        val expectations = state.copy(text1 = value)
        val result = viewModel.state.value
        Assert.assertEquals(expectations, result)
    }

    // endregion

    // region ２つ目の入力値変更

    /**
     * ２つ目の入力欄変更
     *
     * 条件：なし
     * 期待結果：２つ目の入力欄設定値が変更されること
     */
    @Test
    fun changeText2() {
        val state = TextContract.State()
        val value = "hello world"
        viewModel.setEvent(TextContract.Event.ChangeText2(value))
        val expectations = state.copy(text2 = value)
        val result = viewModel.state.value
        Assert.assertEquals(expectations, result)
    }

    // endregion

    // region ３つ目の入力値変更

    /**
     * ３つ目の入力欄変更
     *
     * 条件：なし
     * 期待結果：３つ目の入力欄設定値が変更されること
     */
    @Test
    fun changeText3() {
        val state = TextContract.State()
        val value = "こんにちは"
        viewModel.setEvent(TextContract.Event.ChangeText3(value))
        val expectations = state.copy(text3 = value, textResult = value)
        val result = viewModel.state.value
        Assert.assertEquals(expectations, result)
    }

     // endregion

}