package com.myapp.composesample.ui.right

import javax.inject.Inject


/**
 * 外部に保存されている画像情報アクセス用Repository
 *
 */
interface RemotePhotoRepository{
    /**
     * 画像送信履歴取得
     *
     * @param index
     * @return 画像送信履歴リスト
     */
    suspend fun getSendPhotoHistory(index: Int): List<SendPhotoHistoryData>
}

class RemotePhotoRepositoryImp @Inject constructor(): RemotePhotoRepository{

    override suspend fun getSendPhotoHistory(index: Int): List<SendPhotoHistoryData> {
       // mock
        return listOf(
            SendPhotoHistoryData(1, "2021.11.06", "山田太郎", 1,""),
            SendPhotoHistoryData(2, "2021.11.07", "山田花子", 1,""),
            SendPhotoHistoryData(3, "2021.11.08", "山田高尾", 2,""),
            SendPhotoHistoryData(4, "2021.11.09", "山田次郎", 3,""),
            SendPhotoHistoryData(5, "2021.11.10", "山田モバイル", 1,""),
        )
    }

}

/**
 * 内部に保存されている画像情報アクセス用Repository
 *
 */
interface LocalPhotoRepository{

    /**
     * 画像IDに一致する画像送信情報取得
     *
     * @param id
     * @return
     */
    suspend fun findPhotoDataById(id: Long) : PhotoSendStateEntity?
}

class LocalPhotoRepositoryImp @Inject constructor(): LocalPhotoRepository{
    override suspend fun findPhotoDataById(id: Long): PhotoSendStateEntity? {
       return when(id){
           1L -> PhotoSendStateEntity(1,1,3,5,true,1,1)
           2L -> PhotoSendStateEntity(2,1,3,5,true,1,1)
           3L -> PhotoSendStateEntity(3,1,3,5,false,1,1)
           4L -> PhotoSendStateEntity(4,1,3,5,true,1,1)
           5L -> PhotoSendStateEntity(5,1,3,5,true,1,1)
           else -> null
        }
    }

}