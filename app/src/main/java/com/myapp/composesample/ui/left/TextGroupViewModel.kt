package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel

/**
 * ロジック関連画面 UIロジック
 *
 */
class TextGroupViewModel :
    BaseViewModel<TextContract.State, TextContract.Effect, TextContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): TextContract.State {
        return TextContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: TextContract.Event) = when (event) {
        is TextContract.Event.ChangeTxt1 -> changeText1(event.text)
        is TextContract.Event.ChangeTxt2 -> changeText2(event.text)
        is TextContract.Event.ChangeTxt3 -> changeText3(event.text)
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
