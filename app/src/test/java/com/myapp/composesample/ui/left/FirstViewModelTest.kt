package com.myapp.composesample.ui.left

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 *  First画面 ロジック仕様
 *
 */
class FirstViewModelTest {

    private lateinit var viewModel:  FirstViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

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

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(state: FirstContract.State, effect: FirstContract.Effect?) {
        val resultState = viewModel.state.value
        var resultEffect: FirstContract.Effect? = null
        viewModel.effect
            .onEach { resultEffect = it }
            .launchIn(testScope)
        // 比較
        assertEquals(state, resultState)
        assertEquals(effect, resultEffect)
    }

    // region ボタン関連画面遷移

    /**
     * ボタン関連画面遷移億アクション発火
     *
     * 条件：なし
     * 期待結果：ボタン関連画面遷移エフェクトが発行される
     *
     */
    @ExperimentalCoroutinesApi
    @Test
    fun navigateToButtonGroupScreen() = runBlocking {
        // 期待結果
        val expectationsState = FirstContract.State()
        val expectationsEffect = FirstContract.Effect.NavigateToButtonGroupScreen
        // 実行
        viewModel.setEvent(FirstContract.Event.NavigateToButtonGroupScreen)
        // 検証
        result(expectationsState, expectationsEffect)
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
    @ExperimentalCoroutinesApi
    @Test
    fun navigateToLogicGroupScreen() = testScope.runBlockingTest {
        // 期待値
        val expectationsStatus = FirstContract.State()
        val expectationsEffect= FirstContract.Effect.NavigateToLogicScreen
        //　実施
        viewModel.setEvent(FirstContract.Event.NavigateToLogicScreen)
        // 結果
        val resultState = viewModel.state.value
        var resultEffect: FirstContract.Effect? = null
        viewModel.effect
            .onEach { resultEffect = it }
            .launchIn(testScope)
        // 比較
        assertEquals(expectationsEffect, resultEffect)
        assertEquals(expectationsStatus, resultState)
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
    @ExperimentalCoroutinesApi
    @Test
    fun navigateToTextGroupScreen() = runBlocking {
        // 期待結果
        val expectationsState = FirstContract.State()
        val expectationsEffect = FirstContract.Effect.NavigateToTextGroupScreen
        // 実行
        viewModel.setEvent(FirstContract.Event.NavigateToTextGroupScreen)
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

}