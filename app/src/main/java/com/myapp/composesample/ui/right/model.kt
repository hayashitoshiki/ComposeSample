package com.myapp.composesample.ui.right

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable


// TODO :　モデルへ格納
// ====================
//   DBデータ
// ====================
/**
 * 画像基本情報テーブル
 *
 * @property id プライマリキー
 * @property name
 * @property comment
 * @property imageSize
 * @property createAt
 * @property updateAt
 */
data class PhotoInformationEntity(
    val id: Long,
    val name: String,
    val comment: String,
    val imageSize: Int,
    val createAt: Long,
    val updateAt: Long
)

/**
 * 暗号化された送信予定画像テーブル
 *
 * @property id プライマリキー
 * @property PhotoInformationEntity 画像ID
 * @property index 送信するIndex番目
 * @property photo 画像データ
 * @property createAt 作成日時
 * @property updateAt 更新日時
 */
data class PhotoEntity(
    val id: Long,
    val PhotoInformationEntity: Long,
    val index: Int,
    val photo: Long,
    val createAt: Long,
    val updateAt: Long
)

/**
 * 画像送信状態保持テーブル
 *
 * @property id プライマリキー
 * @property photoInformationId 画像基本情報テーブルID
 * @property sendCount 送信済カウント
 * @property totalSendCount 合計送信枚数
 * @property sendStatus 送信状態ステータス　１：送信中　２：送信完了　３：送信エラー
 * @property createAt 作成時間
 * @property updateAt 更新時間
 */
data class PhotoSendStateEntity(
    val id: Long,
    val photoInformationId: Long,
    val sendCount: Int,
    val totalSendCount: Int,
    val sendStatus: Boolean,
    val createAt: Long,
    val updateAt: Long
)

// ====================
//   レスポンスデータ
// ====================
/**
 * 送信画像取得履歴レスポンス
 *
 * @property code 結果コード
 * @property error エラーメッセージ
 * @property list 送信画像履歴
 * @property hasNext 次のページの有無
 */
data class SendPhotoHistoryResult(
    val code: Int,
    val error: String,
    val list: List<SendPhotoHistoryData>,
    val hasNext: Boolean
)

/**
 * 送信画像履歴レスポンスデータ
 *
 * @property id 送信画像ID
 * @property sendDate 送信日時
 * @property name 顧客指名
 * @property status 送信ステータス
 * @property memo メモ
 */
data class SendPhotoHistoryData(
    val id: Long,
    val sendDate: String,
    val name: String,
    val status: Int,
    val memo: String
)

// ====================
//   ドメインモデル
// ====================

/**
 * 送信履歴モデル
 *
 * @property id 画像ID
 * @property date 日付
 * @property memo メモ
 * @property state 送信ステータス
 */
data class SendPhotoHistory(
    val id: Long,
    var date: String,
    var memo: String,
    var state: PhotoSendState
)


/**
 * 送信ステータス
 *
 */
sealed class PhotoSendState {
    // 送信エラー
    object Error : PhotoSendState()
    // 送信中
    data class Sending(val progress: Int) : PhotoSendState()
    // 送信完了
    object Done : PhotoSendState()
    // 管理者確認済
    object Confirmed : PhotoSendState()
}
