package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.myapp.composesample.R
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.NonDisposableHandle.parent

/**
 * テキスト関連発展画面
 *
 */
@Composable
fun ListGroupScreen(viewModel: ListGroupViewModel) {

    // state
    val state = viewModel.state.value

    // event
    val addTextList: () -> Unit = {
        viewModel.setEvent(ListGroupContract.Event.AddTextList)
    }
    val deleteTextList: () -> Unit = {
        viewModel.setEvent(ListGroupContract.Event.DeleteTextList)
    }


    // 画面描画
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ListGroupScreenMainContent(
                state,
                addTextList,
                deleteTextList
            )
        }
    }
}

/**
 * コンテンツ
 *
 */
@Composable
private fun ListGroupScreenMainContent(
    state: ListGroupContract.State,
    addTextList: () -> Unit,
    removeTextList: () -> Unit
) {
    // 背景タップ設定
    val focusManager = LocalFocusManager.current
    // テキストリストテーブル項目
    val listChunked = state.textList.chunked(3)
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        val (txtTitle1, btnAddTextList, btnRemoveTextList, listText, edt1Input2Error, txtTitle3, edt1Input3) = createRefs()

        SubTitleText(
            "テキストリスト",
            modifier = Modifier.constrainAs(txtTitle1) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        )

        // 「+」「-』ボタン
        Button(
            onClick = { addTextList() },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .constrainAs(btnAddTextList) {
                    top.linkTo(txtTitle1.bottom, margin = 16.dp)
                    start.linkTo(txtTitle1.start)
                }
        ) {
            Text(stringResource(id = R.string.btn_plus))
        }
        Button(
            onClick = { removeTextList() },
            shape = MaterialTheme.shapes.large,
            modifier = Modifier
                .constrainAs(btnRemoveTextList) {
                top.linkTo(btnAddTextList.top)
                start.linkTo(btnAddTextList.end, margin = 32.dp)
            }
        ) {
            Text(stringResource(id = R.string.btn_minus))
        }

        // テキストリストテーブル表示
        Column(
            modifier = Modifier.constrainAs(listText) {
                top.linkTo(btnRemoveTextList.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }
        ) {
            for (textList in listChunked) {
                Row {
                    for (text in textList) TextListItem(text)
                }
            }
        }
    }
}

/**
 * テキスリスト表示用のItem
 *
 */
@Composable
private fun TextListItem(text: String) {
    Card(
        backgroundColor = Color.Red,
        modifier = Modifier
            .size(124.dp)
            .padding(4.dp),
        elevation = 8.dp,
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color(0xFFFFFFFF),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
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
fun PreviewListGroupScreen() {
    val viewModel = ListGroupViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        ListGroupScreen(viewModel)
    }
}
