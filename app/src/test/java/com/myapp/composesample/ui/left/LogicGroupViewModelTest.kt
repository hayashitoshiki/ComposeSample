package com.myapp.composesample.ui.left

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LogicGroupViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    private lateinit var viewModel: LogicGroupViewModel

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

    // region 初期化処理

    /**
     * 初期化
     *
     * 条件：createメソッドが走っていない
     * 期待結果：非同期カウンターが０であること
     */
    @Test
    fun initByNotCreateView() {
        val state = LogicContract.State()
        val expectations = state.copy()
        val result = viewModel.state.value
        assertEquals(expectations, result)
    }

    /**
     * 初期化
     *
     * 条件：createメソッドが走りきっている
     * 期待結果：非同期カウンターが10000000であること
     */
    @Test
    fun initByCreateView() = runBlocking {
        val state = LogicContract.State()
        val value = 10000000
        viewModel.setEvent(LogicContract.Event.CreatedView)
        launch {
            while(true) {
                if (viewModel.state.value.initCounter == 10000000) break
            }
        }.join()
        val expectations = state.copy(initCounter = value)
        val result = viewModel.state.value
        assertEquals(expectations, result)
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
        val state = LogicContract.State()
        val value = 10000000
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
        val expectations = state.copy(initCounter = value)
        val result = viewModel.state.value
        assertNotEquals(expectations, result)
    }

    // endregion


    // region カウントアップ

    /**
     * カウントアップ
     *
     * 条件：なし
     * 期待結果：カウンターが１増えること
     */
    @Test
    fun countUp() {
        val state = LogicContract.State()
        viewModel.setEvent(LogicContract.Event.CountUp)
        val expectations = state.copy(buttonCounter = state.buttonCounter + 1)
        val result = viewModel.state.value
        assertEquals(expectations, result)
    }

    // endregion

    // region カウントダウン

    /**
     * カウントダウン
     *
     * 条件：なし
     * 期待結果：カウンターが１減ること
     */
    @Test
    fun countDown() {
        val state = LogicContract.State()
        viewModel.setEvent(LogicContract.Event.CountDown)
        val expectations = state.copy(buttonCounter = state.buttonCounter - 1)
        val result = viewModel.state.value
        assertEquals(expectations, result)
    }

    // endregion

    // region テキスト変更

    /**
     * テキスト変更
     *
     * 条件：なし
     * 期待結果：引数で渡した値がテキスト設定値に設定されること
     */
    @Test
    fun changeText() {
        val state = LogicContract.State()
        val value = "test data"
        viewModel.setEvent(LogicContract.Event.OnChangeText(value))
        val expectations = state.copy(text = value)
        val result = viewModel.state.value
        assertEquals(expectations, result)
    }

    // endregion

}