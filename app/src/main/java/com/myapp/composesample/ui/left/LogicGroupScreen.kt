package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.composesample.R

/**
 * ロジック関連画面
 *
 */
@Composable
fun LogicGroupScreen(logicGroupViewModel: LogicGroupViewModel) {

    // 初期表示
    LaunchedEffect(true) {
        logicGroupViewModel.createView()
    }

    // state
    val initCounter: Int by logicGroupViewModel.initCounter.observeAsState(0)
    val buttonCounter: Int by logicGroupViewModel.buttonCounter.observeAsState(0)
    val text: String by logicGroupViewModel.text.observeAsState("")

    // event
    val countUpEvent: () -> Unit = { logicGroupViewModel.countUp() }
    val countDownEvent: () -> Unit = { logicGroupViewModel.countDown() }
    val onChangeText: (String) -> Unit = { logicGroupViewModel.onChangeText(it) }

    // 描画
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            LogicScreenMainContent(
                initCounter,
                buttonCounter,
                text,
                countUpEvent,
                countDownEvent,
                onChangeText
            )
        }
    }
}

/**
 * 画面描画
 *
 */
@Composable
private fun LogicScreenMainContent(
    initCounter: Int,
    buttonCounter: Int,
    text: String,
    countUpEvent : () -> Unit,
    countDownEvent : () -> Unit,
    onChangeText: (String) -> Unit
) {
    Column {

        // 画面描画時非同期取得
        LogicScreenSubTitle("画面描画時非同期取得")
        Text(
            text = initCounter.toString(),
            modifier = Modifier.padding(top = 8.dp),
        )

        // + , - ボタン
        LogicScreenSubTitle("カウントアップ")
        Row(Modifier.selectableGroup()) {
            Button(
                onClick = { countUpEvent() },
                modifier = Modifier.padding(top = 8.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(stringResource(id = R.string.btn_plus))
            }

            Button(
                onClick = { countDownEvent() },
                modifier = Modifier.padding(top = 8.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(stringResource(id = R.string.btn_minus))
            }
        }
        Text(
            text = buttonCounter.toString(),
            modifier = Modifier.padding(top = 8.dp),
        )

        // Text変更
        LogicScreenSubTitle("テキスト変更")
        OutlinedTextField(
            value = text,
            onValueChange = { onChangeText(it) },
            label = { Text("Name") }
        )
        Text(
            text = text,
            modifier = Modifier.padding(top = 8.dp),
        )

    }
}

/**
 * サブタイトル
 *
 * @param title サブタイトルテキスト
 */
@Composable
private fun LogicScreenSubTitle(title: String) {
    Text(
        title,
        fontStyle = FontStyle.Italic,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

/**
 * プレビュー表示
 *
 */
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
fun PreviewLogicScreen() {
    val logicGroupViewModel = LogicGroupViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        LogicGroupScreen(logicGroupViewModel)
    }
}
