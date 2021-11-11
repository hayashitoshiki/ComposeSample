package com.myapp.composesample.ui.right

import androidx.lifecycle.*
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
    private val _enabledAddPhotoButton: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>()
    val enabledAddPhotoButton: LiveData<Boolean> = _enabledAddPhotoButton
    private val _enabledSendButton: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>()
    val enabledSendButton: LiveData<Boolean> = _enabledSendButton
    private val _photoList: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val photoList: LiveData<List<String>> = _photoList
    private val _deleteIndex: MutableLiveData<Int> = MutableLiveData<Int>(-1)
    val deleteIndex: LiveData<Int> = _deleteIndex
    private val _isDeleteDialog: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDeleteDialog: LiveData<Boolean> = _isDeleteDialog
    private val _isOpenCameraView: MutableLiveData<Boolean> = MutableLiveData(false)
    val isOpenCameraView: LiveData<Boolean> = _isOpenCameraView

    // error
    private val _lastNameErrorMessage: MutableLiveData<String> = MutableLiveData<String>("")
    val lastNameErrorMessage: LiveData<String> = _lastNameErrorMessage
    private val _commentErrorMessage: MutableLiveData<String> = MutableLiveData<String>("")
    val commentErrorMessage: LiveData<String> = _commentErrorMessage

    // effect
    private val _effect: Channel<Effect> = Channel()
    val effect: Flow<Effect> = _effect.receiveAsFlow()


    init {
        _enabledAddPhotoButton.addSource(_photoList){ it.size < 20 }
        _enabledSendButton.addSource(_firstNameText){ changeEnableSendButton() }
        _enabledSendButton.addSource(_lastNameErrorMessage){ changeEnableSendButton() }
        _enabledSendButton.addSource(_photoList){ changeEnableSendButton() }
    }

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

    fun add(uri: String) {
        val list = _photoList.value?.toMutableList() ?: return
        list.add(uri)
        _photoList.postValue(list)
        _isOpenCameraView.postValue(false)
    }

    fun openCameraView(){
        _isOpenCameraView.value = true
    }

    fun delete(index: Int) {
        val list = _photoList.value?.toMutableList() ?: return
        if (!(0 <= index && index < list.size)) return
        val fileName = list[index]
        list.remove(fileName)
        _photoList.value = list
        clearDeleteDialog()
       // usecase.deletePhotoFile(fileName)
    }

    fun openDeleteDialog(index: Int) {
        _deleteIndex.value = index
        _isDeleteDialog.value = true
    }

    fun clearDeleteDialog() {
        _deleteIndex.value = -1
        _isDeleteDialog.value = false
    }

    private fun changeEnableSendButton() {
        val firstName = _firstNameText.value ?: return
        val lastName = _lastNameText.value ?: return
        val listSize = _photoList.value?.size ?: return
        _enabledSendButton.value = when {
            firstName.isBlank() -> false
            lastName.isBlank() -> false
            listSize == 0 -> false
             else -> true
        }
    }

    /**
     * 送信ボタン押下
     *
     */
    fun onClickSend() {
        // TODO : 送信処理
        viewModelScope.launch { _effect.send(Effect.FocusChangeLastNameToComment) }
    }

    /**
     * 画像削除
     *
     */
    fun deleteImage() {
        // TODO : 画像削除
    }

    /**
     * UIイベント
     *
     */
    enum class Effect {
        // 名入力蘭へフォーカス移動
        FocusChangeFirstNameToLastName,
        // コメント入力欄へフォーカス移動
        FocusChangeLastNameToComment,
        // 写真送信開始画面へ遷移
        NavigateToSendPhotoStart
    }
}
