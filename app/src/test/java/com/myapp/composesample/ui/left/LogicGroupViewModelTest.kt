package com.myapp.composesample.ui.left

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 *  ロジック関連画面 ロジック仕様
 *
 */
class LogicGroupViewModelTest {

    private val state = LogicContract.State()

    private lateinit var viewModel: LogicGroupViewModel

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        viewModel = LogicGroupViewModel()
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
    private fun result(state: LogicContract.State, effect: LogicContract.Effect?) {
        val resultState = viewModel.state.value
        var resultEffect: LogicContract.Effect? = null
        viewModel.effect
            .onEach { resultEffect = it }
            .launchIn(testScope)
        // 比較
        assertEquals(state, resultState)
        assertEquals(effect, resultEffect)
    }

    // region 初期化処理

    /**
     * 初期化
     *
     * 条件：createメソッドが走っていない
     * 期待結果：非同期カウンターが０であること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByNotCreateView() {
        // 期待結果
        val expectationsState = state.copy()
        val expectationsEffect = null
        // 実行・検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期化
     *
     * 条件：createメソッドが走りきっている
     * 期待結果：非同期カウンターが10000000であること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByCreateView()  = testScope.runBlockingTest {
        val value = 10000000
        // 期待結果
        val expectationsState =  state.copy(initCounter = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(LogicContract.Event.CreatedView)
        launch {
            while(true) {
                if (viewModel.state.value.initCounter == 10000000) break
            }
        }.join()
        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * 初期化
     *
     * 条件：createメソッドが走っている途中に中断処理が走る
     * 期待結果：非同期カウンターが10000000でない(途中で止まる)こと
     */
    @ExperimentalCoroutinesApi
    @Test
    fun initByCreateViewStop() = testScope.runBlockingTest {
        val value = 10000000
        // 期待結果
        val expectationsState =  state.copy(initCounter = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(LogicContract.Event.CreatedView)
        launch {
            while(true) {
                if (viewModel.state.value.initCounter == 10000000) break
                if (viewModel.state.value.initCounter > 10) {
                    viewModel.setEvent(LogicContract.Event.Disposable)
                    break
                }
            }
        }.join()
        // 検証
        assertNotEquals(expectationsState, expectationsEffect)
    }

    // endregion


    // region カウントアップ

    /**
     * カウントアップ
     *
     * 条件：なし
     * 期待結果：カウンターが１増えること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun countUp() = testScope.runBlockingTest {
        // 期待結果
        val expectationsState = state.copy(buttonCounter = state.buttonCounter + 1)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(LogicContract.Event.CountUp)
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region カウントダウン

    /**
     * カウントダウン
     *
     * 条件：なし
     * 期待結果：カウンターが１減ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun countDown() = testScope.runBlockingTest {
        // 期待値
        val expectationsState = state.copy(buttonCounter = state.buttonCounter - 1)
        val expectationsEffect = null
        //　実行
        viewModel.setEvent(LogicContract.Event.CountDown)
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region テキスト変更

    /**
     * テキスト変更
     *
     * 条件：なし
     * 期待結果：引数で渡した値がテキスト設定値に設定されること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeText() = testScope.runBlockingTest {
        // 定数
        val value = "test data"
        // 期待結果
        val expectationsState = state.copy(text = value)
        val expectationsEffect = null
        // 実行
        viewModel.setEvent(LogicContract.Event.OnChangeText(value))
        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

}