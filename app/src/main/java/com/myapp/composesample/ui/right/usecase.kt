package com.myapp.composesample.ui.right

import javax.inject.Inject

/**
 * 画像送信に関するビジネスロジック
 *
 */
interface PhotoUseCase {

    /**
     * 画像送信履歴取得
     *
     * @param index 取得するページ番号
     * @return 画像送信履歴
     */
    suspend fun updateSendPhotoList(index: Int): List<SendPhotoHistory>
}

class PhotoUseCaseImp @Inject constructor(
    private val remotePhotoRepository: RemotePhotoRepository,
    private val localPhotoRepository: LocalPhotoRepository,
) : PhotoUseCase{

    override suspend fun updateSendPhotoList(index: Int): List<SendPhotoHistory>{
        val list = remotePhotoRepository.getSendPhotoHistory(index)
        return list.map { sendPhotoHistoryData ->
            val id = sendPhotoHistoryData.id
            val date = sendPhotoHistoryData.sendDate
            val memo = sendPhotoHistoryData.memo
            var state: PhotoSendState = PhotoSendState.Error
            localPhotoRepository.findPhotoDataById(id)?.let { photoPhotoSendStateEntity ->
                state = when {
                    !photoPhotoSendStateEntity.sendStatus -> PhotoSendState.Error
                    photoPhotoSendStateEntity.sendCount == photoPhotoSendStateEntity.totalSendCount -> {
                        if (sendPhotoHistoryData.status == 2) {
                            PhotoSendState.Confirmed
                        } else {
                            PhotoSendState.Done
                        }
                    }
                    else -> {
                        val ratio: Int = (photoPhotoSendStateEntity.sendCount.toDouble() / photoPhotoSendStateEntity.totalSendCount.toDouble()* 100).toInt()
                        PhotoSendState.Sending(ratio)
                    }
                }
            } ?: run {
                state = when(sendPhotoHistoryData.status) {
                    1 -> PhotoSendState.Done
                    2 -> PhotoSendState.Confirmed
                    3 -> return@map null
                    else -> return@map null
                }
            }
            return@map SendPhotoHistory(id,  date, memo,  state)
        }.filterNotNull()


        // リストの件数更新
    }
}
