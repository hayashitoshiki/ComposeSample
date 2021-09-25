package com.myapp.composesample.ui.left

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class FirstViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel:  FirstViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        viewModel = FirstViewModel()
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // region ボタン関連画面遷移

    /**
     * ボタン関連画面遷移億アクション発火
     *
     * 条件：なし
     * 期待結果：ボタン関連画面遷移エフェクトが発行される
     *
     */
    @Test
    fun navigateToButtonGroupScreen() = runBlocking {
        viewModel.setEvent(FirstContract.Event.NavigateToButtonGroupScreen)
        val expectations = FirstContract.Effect.NavigateToButtonGroupScreen
        val result = viewModel.effect.first()
        assertEquals(expectations, result)
    }

    // endregion

    // region ロジック関連画面遷移

    /**
     * ロジック関連画面遷移億アクション発火
     *
     * 条件：なし
     * 期待結果：ロジック関連画面遷移エフェクトが発行される
     *
     */
    @Test
    fun navigateToLogicGroupScreen() = runBlocking {
        viewModel.setEvent(FirstContract.Event.NavigateToLogicScreen)
        val expectations = FirstContract.Effect.NavigateToLogicScreen
        val result = viewModel.effect.first()
        assertEquals(expectations, result)
    }

    // endregion

    // region テキスト関連画面遷移

    /**
     * テキスト関連画面遷移億アクション発火
     *
     * 条件：なし
     * 期待結果：テキスト関連画面遷移エフェクトが発行される
     *
     */
    @Test
    fun navigateToTextGroupScreen() = runBlocking {
        viewModel.setEvent(FirstContract.Event.NavigateToTextGroupScreen)
        val expectations = FirstContract.Effect.NavigateToTextGroupScreen
        val result = viewModel.effect.first()
        assertEquals(expectations, result)
    }

    // endregion

}