package com.myapp.composesample.util.component

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.composesample.R
import com.myapp.composesample.ui.right.PhotoSendState
import com.myapp.composesample.ui.right.SendPhotoHistory
import com.myapp.composesample.util.theme.*


/**
 * 写真送信履歴項目
 *
 * @param modifier レイアウト
 * @param itemList 写真送信履歴リスト
 * @param enabledMemo メモボタンの活性非活性
 * @param enabledStatus ステータスボタンの活性非活性
 * @param onClickActionMemo メモボタンのクリックアクション
 * @param onClickActionState ステータスボタンのクリックアクション
 */
@Composable
fun SendPhotoHistoryTopic(
    modifier: Modifier = Modifier,
    itemList: List<SendPhotoHistory>,
    enabledMemo: Boolean = true,
    enabledStatus: Boolean = true,
    onClickActionMemo: (Int) -> Unit = {},
    onClickActionState: (Int) -> Unit = {},
) {
    Column(modifier = modifier) {
        Row{
            SubTitleImage(resId = R.drawable.ic_phone_android_black_48)
            SubTitleGroup(
                text = "写真送信履歴",
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        SendHistoryList(
            itemList = itemList,
            enabledMemo = enabledMemo,
            enabledStatus = enabledStatus,
            onClickActionMemo = onClickActionMemo,
            onClickActionState = onClickActionState,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

/**
 * 写真送信履歴リスト（クリックリスナーつける）
 *
 * @param modifier レイアウト
 * @param itemList 写真送信履歴リスト
 * @param enabledMemo メモボタンの活性非活性
 * @param enabledStatus ステータスボタンの活性非活性
 * @param onClickActionMemo メモボタンのクリックアクション
 * @param onClickActionState ステータスボタンのクリックアクション
 */
@Composable
private fun SendHistoryList(
    modifier: Modifier,
    itemList: List<SendPhotoHistory>,
    enabledMemo: Boolean = true,
    enabledStatus: Boolean = true,
    onClickActionMemo: (Int) -> Unit = {},
    onClickActionState: (Int) -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (itemList.isNotEmpty()) {
            SendHistoryListHeader()
            itemList.forEachIndexed { index, item ->
                SendHistoryListItem(
                    index = index,
                    item = item,
                    enabledMemo = enabledMemo,
                    enabledStatus = enabledStatus,
                    onClickActionMemo = onClickActionMemo,
                    onClickActionState = onClickActionState
                )
            }
        } else {
            Text(
                text = "送信履歴はありません。",
                fontSize = 14.sp,
                color = TextColor
            )
        }
    }
}

/**
 * 写真送信履歴リストヘッダー
 *
 */
@Composable
private fun SendHistoryListHeader() {
    Column{
        Box(modifier = Modifier.fillMaxWidth()) {
            SendHistoryListHeaderColumn(text = "送信日時")
            SendHistoryListHeaderColumn(
                text = "ステータス",
                modifier = Modifier
                    .width(90.dp)
                    .align(alignment = Alignment.BottomEnd)
            )
        }
        Divider(color = BoundaryColor, modifier = Modifier.padding(top = 5.dp, bottom = 20.dp))
    }

}

/**
 * 写真送信履歴リストヘッダーカラム名
 *
 * @param text カラム名
 * @param modifier レイアウト
 */
@Composable
private fun SendHistoryListHeaderColumn(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = TextColor
    )
}

/**
 * 写真送信履歴リストItem
 *
 * @param index アイテムの項番
 * @param item 写真送信履歴リスト
 * @param enabledMemo メモボタンの活性非活性
 * @param enabledStatus ステータスボタンの活性非活性
 * @param onClickActionMemo メモボタンのクリックアクション
 * @param onClickActionState ステータスボタンのクリックアクション
 */
@Composable
private fun SendHistoryListItem(
    index: Int,
    item: SendPhotoHistory,
    enabledMemo: Boolean,
    enabledStatus: Boolean,
    onClickActionMemo: (Int) -> Unit,
    onClickActionState: (Int) -> Unit,
) {
    Column {
        Box(modifier = Modifier.padding(top = 6.dp).fillMaxWidth()) {
            Text(
                text = item.date,
                fontSize = 14.sp,
                color = TextColor,
                modifier = Modifier.align(alignment = Alignment.CenterStart)
            )
            Row(modifier = Modifier.align(alignment = Alignment.BottomEnd)) {
                MemoButton(onClickAction = { onClickActionMemo(index) }, enabled = enabledMemo)
                val statusModifier = Modifier.padding(start = 6.dp)
                when (val state = item.state) {
                    is PhotoSendState.Error -> {
                        SendErrorButton(
                            modifier = statusModifier,
                            onClickAction = { onClickActionState(index) },
                            enabled = enabledStatus
                        )
                    }
                    is PhotoSendState.Sending -> SendingButton(progress = state.progress, modifier = statusModifier)
                    is PhotoSendState.Done -> SendDoneButton(modifier = statusModifier)
                    is PhotoSendState.Confirmed -> SendConfirmedButton(modifier = statusModifier)
                }
            }
        }
        Divider(color = BoundaryColor, modifier = Modifier.padding(top = 5.dp))
    }
}

/**
 * メモボタン
 *
 * @param onClickAction クリックアクション
 * @param enabled 活性非活性
 */
@Composable
private fun MemoButton(onClickAction: () -> Unit, enabled :Boolean) {
    OutlinedButton(
        onClick = { onClickAction()},
        enabled = enabled,
        border= BorderStroke(1.dp, IconCheckedColor),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = TransparentColor,
            contentColor = IconCheckedColor,
            disabledContentColor = IconCheckedColor
        ),
        modifier = Modifier.height(28.dp).width(50.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = "メモ",
            fontSize = 13.sp,
            color = IconCheckedColor
        )
    }
}

/**
 * 送信失敗ボタン
 *
 * @param modifier レイアウト
 * @param onClickAction クリックアクション
 * @param enabled 活性非活性
 */
@Composable
private fun SendErrorButton(modifier: Modifier, onClickAction: () -> Unit, enabled :Boolean) {
    Button(
        onClick = { onClickAction() },
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = IconCheckedColor,
            contentColor = IconCheckedColor,
            disabledContentColor = IconCheckedColor
        ),
        modifier = modifier
            .height(28.dp)
            .width(90.dp)
    ) {
        Text(
            text = "送信失敗",
            fontSize = 13.sp,
            color = TextColorLight
        )
    }
}

/**
 * 送信中ボタン
 *
 * @param progress 進捗率
 * @param modifier レイアウト
 */
@Composable
private fun SendingButton(progress: Int, modifier: Modifier) {
    Button(
        onClick = {},
        enabled = false,
        contentPadding = PaddingValues(vertical = 5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = ButtonPhotoSendingColor,
            contentColor = ButtonPhotoSendingColor,
            disabledContentColor = ButtonPhotoSendingColor
        ),
        modifier = modifier
            .height(28.dp)
            .width(90.dp)
    ) {
        Text(
            text = "$progress%送信",
            fontSize = 13.sp,
            color = TextPhotoSendingColor
        )
    }
}

/**
 * 送信完了ボタン
 *
 * @param modifier レイアウト
 */
@Composable
private fun SendDoneButton(modifier: Modifier) {
    Button(
        onClick = {},
        enabled = false,
        contentPadding = PaddingValues(vertical = 5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = ButtonPhotoDoneColor,
            contentColor = ButtonPhotoDoneColor,
            disabledContentColor = ButtonPhotoDoneColor
        ),
        modifier = modifier
            .height(28.dp)
            .width(90.dp)
    ) {
        Text(
            text = "送信完了",
            fontSize = 13.sp,
            color = TextColorLight
        )
    }
}

/**
 * 管理者確認済ボタン
 *
 * @param modifier レイアウト設定
 */
@Composable
private fun SendConfirmedButton(modifier: Modifier) {
    Button(
        onClick = {},
        enabled = false,
        contentPadding = PaddingValues(vertical = 5.dp),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = ButtonPhotoConfirmedColor,
            contentColor = ButtonPhotoConfirmedColor,
            disabledContentColor = ButtonPhotoConfirmedColor
        ),
        modifier = modifier
            .height(28.dp)
            .width(90.dp)
    ) {
        Text(
            text = "管理者確認済",
            fontSize = 11.sp,
            color = TextColorLight
        )
    }
}

/**
 * 写真送信履歴一覧画面遷移ボタン
 *
 * @param modifier レイアウト
 * @param onClickAction クリックアクション
 */
@Composable
fun SendPhotoHistoryListButton(modifier: Modifier, onClickAction: () -> Unit) {
    TextButton(
        onClick = { onClickAction() },
        modifier =  modifier
    ) {
        Text(
            text = "写真送信履歴一覧 >",
            fontSize = 16.sp,
            color = IconGreenColor
        )
    }
}

/**
 * 各トピックのタイトル
 *
 * @param text トピックタイトル
 * @param modifier レイアウト
 */
@Composable
fun SubTitleGroup(text: String, modifier: Modifier = Modifier) {
    Column {
        SubTitleText(
            text = text,
            modifier = modifier
        )
        Divider(
            color = BoundaryColor,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}
/**
 * 各トピックのタイトルテキスト
 *
 * @param text トピックタイトル
 * @param modifier レイアウト
 */
@Composable
fun SubTitleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = TextColor,
        modifier = modifier
    )
}

/**
 * 各トピックのタイトル
 *
 * @param resId イメージリソース
 * @param modifier レイアウト
 */
@Composable
private fun SubTitleImage(@DrawableRes resId: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(resId),
        contentDescription = null,
        modifier = modifier
            .size(29.dp)
            .clip(CircleShape)
            .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
    )
}

/**
 * 各トピックのタイトルテキスト
 *
 * @param text トピックタイトル
 * @param modifier レイアウト
 */
@Composable
fun BigWhiteImageButton(
    text: String,
    @DrawableRes resId: Int,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { onClickAction() },
        modifier = modifier
            .padding(top = 30.dp)
            .height(112.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null,
            modifier = Modifier.size(82.dp)
        )
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = TextColor,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Preview(
    showBackground = true,
    name = "Light Mode"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun SendPhotoHistoryTopicDemo() {
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val sendHistoryListByNull: List<SendPhotoHistory> = listOf()
            val sendHistoryList = listOf(
                SendPhotoHistory(1,"2021.04.20 15:00:00", "", PhotoSendState.Error),
                SendPhotoHistory(2, "2021.05.01 15:00:00", "", PhotoSendState.Done),
                SendPhotoHistory(3, "2021.04.20 15:00:00", "", PhotoSendState.Confirmed),
                SendPhotoHistory(4, "2021.10.20 15:00:00", "", PhotoSendState.Sending(50))
            )

            SendPhotoHistoryTopic(itemList = sendHistoryList)
            SendPhotoHistoryTopic(itemList = sendHistoryListByNull)
        }
    }
}
