package com.myapp.composesample.ui.left

import com.myapp.composesample.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * テキスト関連発展画面 UIロジック
 *
 */
@HiltViewModel
class TextGroupExtraViewModel @Inject constructor() :
    BaseViewModel<TextGroupExtraContract.State, TextGroupExtraContract.Effect, TextGroupExtraContract.Event>() {

    /**
     * Stateの初期化
     *
     * @return 初期化されたState
     */
    override fun initState(): TextGroupExtraContract.State {
        return TextGroupExtraContract.State()
    }

    /**
     * アクションとUIロジクの紐付け
     *
     * @param event アクション
     */
    override fun handleEvents(event: TextGroupExtraContract.Event) = when (event) {
        is TextGroupExtraContract.Event.ChangeText1 -> changeText1(event.text)
        is TextGroupExtraContract.Event.ChangeText2 -> changeText2(event.text)
        is TextGroupExtraContract.Event.ChangeText3 -> changeText3(event.text)
    }

    /**
     * テキスト変更１
     * ・バリデーションメッセージなし
     * ・エンター切り捨て
     * ・５文字制限あり
     *
     * @param text 変更するテキスト
     */
    private fun changeText1(text: String) {
        // 文字列加工
        var value = text
        if (Regex("\n").containsMatchIn(value)) {
            value = text.split("\n").first()
            setEffect { TextGroupExtraContract.Effect.FocusChangeInput1ToInput2 }
        }
        if (value.length > 5) value = value.substring(0, 5)

        setState { copy(text1 = value) }
    }

    /**
     * テキスト変更2
     * ・バリデーションメッセージあり
     * ・エンター切り捨て
     * ・５文字制限なし
     *
     * @param text 変更するテキスト
     */
    private fun changeText2(text: String) {
        // 文字列加工
        var value = text
        if (Regex("\n").containsMatchIn(value)) {
            value = text.split("\n").first()
            setEffect { TextGroupExtraContract.Effect.FocusChangeInput1ToInput2 }
        }

        // バリデーション処理
        val errorMessage = when {
            value.isBlank() -> "○○を入力してください"
            value.length > 5 -> "○○は５文字以下で入力してください"
            else -> ""
        }

        setState { copy(text2 = value, errorMessageText2 = errorMessage) }
    }

    /**
     * テキスト変更3
     *
     * @param text 変更するテキスト
     */
    private fun changeText3(text: String) {
        setState { copy(text3 = text) }
    }

}
