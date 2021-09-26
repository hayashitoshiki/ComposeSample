package com.myapp.composesample.ui.left

import androidx.lifecycle.viewModelScope
import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * ロジック関連画面 UIロジック
 *
 */
@HiltViewModel
class LogicGroupViewModel @Inject constructor() :
    BaseViewModel<LogicContract.State, LogicContract.Effect, LogicContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): LogicContract.State {
        return LogicContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: LogicContract.Event) = when (event) {
        is LogicContract.Event.CreatedView -> createView()
        is LogicContract.Event.Disposable -> disposable()
        is LogicContract.Event.CountUp -> countUp()
        is LogicContract.Event.CountDown -> countDown()
        is LogicContract.Event.OnChangeText -> onChangeText(event.text)
    }

    private lateinit var job: Job

    /**
     * 初期表示
     *
     */
    private fun createView() {
        job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                for (i in 0..10000000) {
                    if (!this.isActive) return@withContext
                    setState { copy(initCounter = i) }
                }
            }
        }
    }

    private fun disposable() {
        job.cancel()
    }


    /**
     * カウントアップ
     *
     */
    private fun countUp() {
        setState { copy(buttonCounter = this.buttonCounter + 1) }
    }

    /**
     * カウントダウン
     *
     */
    private fun countDown() {
        setState { copy(buttonCounter = this.buttonCounter - 1) }
    }

    /**
     * テキスト変更
     *
     * @param text テキスト変更
     */
    private fun onChangeText(text: String) {
        setState { copy(text = text) }
    }

}
