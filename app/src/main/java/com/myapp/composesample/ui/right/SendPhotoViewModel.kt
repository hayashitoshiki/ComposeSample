package com.myapp.composesample.ui.right

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * 写真送信画面　UIロジック
 *
 */
class SendPhotoViewModel: ViewModel() {

    // state
    private val _firstNameText: MutableLiveData<String> = MutableLiveData<String>("")
    val firstNameText: LiveData<String> = _firstNameText
    private val _lastNameText: MutableLiveData<String> = MutableLiveData<String>("")
    val lastNameText: LiveData<String> = _lastNameText
    private val _commentText: MutableLiveData<String> = MutableLiveData<String>("")
    val commentText: LiveData<String> = _commentText
    private val _enabledAddPhotoButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)
    val enabledAddPhotoButton: LiveData<Boolean> = _enabledAddPhotoButton
    private val _enabledSendButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val enabledSendButton: LiveData<Boolean> = _enabledSendButton

    // error
    private val _lastNameErrorMessage: MutableLiveData<String> = MutableLiveData<String>("")
    val lastNameErrorMessage: LiveData<String> = _lastNameErrorMessage
    private val _commentErrorMessage: MutableLiveData<String> = MutableLiveData<String>("")
    val commentErrorMessage: LiveData<String> = _commentErrorMessage

    // effect
    private val _effect: Channel<Effect> = Channel()
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    /**
     * 姓変更
     * ・バリデーションメッセージなし
     * ・エンター切り捨て
     * ・５文字制限あり
     * ・かな文字のみ入力可
     *
     * @param text 変更するテキスト
     */
    fun changeFirstName(text: String) {
        // 文字列加工
        // TODO : カナ文字以外どうするべきか
        var value = text
        if (Regex("\n").containsMatchIn(value)) {
            value = text.split("\n").first()
            viewModelScope.launch { _effect.send(Effect.FocusChangeFirstNameToLastName) }
        }
        if (value.length > 5) value = value.substring(0, 5)
        _firstNameText.value = value
    }

    /**
     * 名変更
     * ・バリデーションメッセージなし
     * ・エンター切り捨て
     * ・５文字制限あり
     * ・かな文字のみ入力可
     *
     * @param text 変更するテキスト
     */
    fun changeLastName(text: String) {
        // 文字列加工
        // TODO : カナ文字以外どうするべきか
        var value = text
        if (Regex("\n").containsMatchIn(value)) {
            value = text.split("\n").first()
            viewModelScope.launch { _effect.send(Effect.FocusChangeLastNameToComment) }
        }
        if (value.length > 5) value = value.substring(0, 5)

        _lastNameText.value = value
    }

    /**
     * コメント変更
     *
     * @param text 変更するテキスト
     */
    fun changeComment(text: String) {
        // 文字列加工
        var value = text
        if (value.length > 5000) value = value.substring(0, 5000)

        _commentText.value = value
    }

    /**
     * 送信ボタン押下
     *
     */
    fun onClickSend() {
        // TODO : 送信処理
        // 崇信してエラーならエラー値を設定
        // 成功なら画像変換を呼び出し
        // 成功なら返還後、格納して削除
        // 失敗は？？？
    }

    /**
     * 画像削除
     *
     */
    fun deleteImage() {

    }

    enum class Effect {
        FocusChangeFirstNameToLastName,
        FocusChangeLastNameToComment
    }
}
