package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ロジック関連画面 UIロジック
 *
 */
@HiltViewModel
class TextGroupViewModel @Inject constructor() :
    BaseViewModel<TextGroupContract.State, TextGroupContract.Effect, TextGroupContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): TextGroupContract.State {
        return TextGroupContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: TextGroupContract.Event) = when (event) {
        is TextGroupContract.Event.ChangeText1 -> changeText1(event.text)
        is TextGroupContract.Event.ChangeText2 -> changeText2(event.text)
        is TextGroupContract.Event.ChangeText3 -> changeText3(event.text)
    }

    /**
     * テキスト変更１
     *
     * @param text 変更するテキスト
     */
    private fun changeText1(text: String) {
        setState { copy(text1 = text) }
    }

    /**
     * テキスト変更2
     *
     * @param text 変更するテキスト
     */
    private fun changeText2(text: String) {
        setState { copy(text2 = text) }
    }

    /**
     * テキスト変更3
     *
     * @param text 変更するテキスト
     */
    private fun changeText3(text: String) {
        setState { copy(text3 = text, textResult = text) }
    }

}
