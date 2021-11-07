package com.myapp.composesample.ui.right

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * 画像送信に関するDispatcher
 *
 */
object PhotoDispatcher {
    private val _sendState: MutableSharedFlow<PhotoSendStateAction> = MutableSharedFlow()
    val sendState: SharedFlow<PhotoSendStateAction> = _sendState

    suspend fun actionChangeSendPhotoState(action: PhotoSendStateAction) {
        _sendState.emit(action)
    }
}

/**
 * 送信アクション
 *
 * @property id 画像ID
 * @property state 画像状態
 */
data class PhotoSendStateAction(val id: Long, val state: PhotoSendState)