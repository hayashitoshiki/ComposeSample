package com.myapp.composesample.ui.right

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 写真送信Top画面　UIロジック
 *
 */
@HiltViewModel
class SendPhotoTopViewModel @Inject constructor(
    private val photoUseCase: PhotoUseCase
): ViewModel() {

    // state
    private val _sendPhotoHistoryList: MutableLiveData<List<SendPhotoHistory>> = MutableLiveData()
    val sendPhotoHistoryList: LiveData<List<SendPhotoHistory>> = _sendPhotoHistoryList

    // effect
    private val _effect: Channel<Effect> = Channel()
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    init {
        updateSendPhotoList()
        PhotoDispatcher.sendState.onEach { dto ->
            _sendPhotoHistoryList.value
                ?.map {
                    if (it.id == dto.id) it.state = dto.state
                    return@map it
                }
                ?.also{
                    _sendPhotoHistoryList.value = listOf()
                    _sendPhotoHistoryList.value = it
                }
        }.launchIn(viewModelScope)
    }

    /**
     * 画像送信履歴更新
     *
     */
    fun updateSendPhotoList() = viewModelScope.launch {
        runCatching { photoUseCase.updateSendPhotoList(1) }
            .onSuccess {_sendPhotoHistoryList.value = it }
            .onFailure { _effect.send(Effect.NetWorkError) }
    }

    // モック用データ変更処理
    fun changeData() {
        val action = PhotoSendStateAction(1L,PhotoSendState.Error)
        viewModelScope.launch { PhotoDispatcher.actionChangeSendPhotoState(action) }
    }

    enum class Effect {
        NetWorkError
    }
}
