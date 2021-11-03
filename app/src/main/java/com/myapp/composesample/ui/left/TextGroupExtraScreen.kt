package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.myapp.composesample.ui.NavigationScreens
import com.myapp.composesample.util.base.LayoutTag
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * テキスト関連発展画面
 *
 */
@Composable
fun TextGroupExtraScreen(viewModel: TextGroupExtraViewModel) {

    // state
    val state = viewModel.state.value

    // event
    val changeText1: (String) -> Unit = {
        viewModel.setEvent(TextGroupExtraContract.Event.ChangeText1(it))
    }
    val changeText2: (String) -> Unit = {
        viewModel.setEvent(TextGroupExtraContract.Event.ChangeText2(it))
    }
    val changeText3: (String) -> Unit = {
        viewModel.setEvent(TextGroupExtraContract.Event.ChangeText3(it))
    }

    // effect
    val focusManager = LocalFocusManager.current
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is TextGroupExtraContract.Effect.FocusChangeInput1ToInput2 -> {
                    focusManager.moveFocus(FocusDirection.Right)
                }
                is TextGroupExtraContract.Effect.FocusChangeInput2ToInput3 -> {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            }
        }.collect()
    }


    // 画面描画
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextExtraScreenMainContent(
                state,
                changeText1,
                changeText2,
                changeText3
            )
        }
    }
}

/**
 * コンテンツ
 *
 */
@Composable
private fun TextExtraScreenMainContent(
    state: TextGroupExtraContract.State,
    changeText1: (String) -> Unit,
    changeText2: (String) -> Unit,
    changeText3: (String) -> Unit
) {
    // 背景タップ設定
    val focusManager = LocalFocusManager.current
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
        val (txtTitle1, edt1Input1, txtTitle2, edt1Input2, edt1Input2Error, txtTitle3, edt1Input3) = createRefs()

        // テキスト入力欄１
        SubTitleText(
            "入力５文字表示制限あり",
            modifier = Modifier.constrainAs(txtTitle1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )
        OutlinedTextField(
            value = state.text1,
            maxLines = 1,
            onValueChange = { changeText1(it) },
            label = { Text("テキスト入力欄１") },
            modifier = Modifier.width(160.dp)
                .constrainAs(edt1Input1) {
                    top.linkTo(txtTitle1.bottom, margin = 16.dp)
                    start.linkTo(txtTitle1.start, margin = 16.dp)
            }
        )

        // テキスト入力欄２
        SubTitleText(
            "制限なし、チェックあり",
            modifier = Modifier.constrainAs(txtTitle2) {
                top.linkTo(txtTitle1.top)
                start.linkTo(txtTitle1.end, margin = 16.dp)
            }
        )
        OutlinedTextField(
            value = state.text2,
            maxLines = 1,
            isError = state.errorMessageText2.isNotEmpty(),
            onValueChange = { changeText2(it) },
            label = { Text("テキスト入力欄２") },
            modifier = Modifier.width(160.dp)
                .constrainAs(edt1Input2) {
                top.linkTo(txtTitle2.bottom, margin = 16.dp)
                start.linkTo(txtTitle2.start)
            }
        )
        if (state.errorMessageText2.isNotEmpty()) {
            Text(
                text = state.errorMessageText2,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.constrainAs(edt1Input2Error) {
                    top.linkTo(edt1Input2.bottom)
                    start.linkTo(edt1Input2.start)
                }
            )
        }

        // テキスト入力欄３
        SubTitleText(
            "制限なし、バリデーションあり",
            modifier = Modifier.constrainAs(txtTitle3) {
                top.linkTo(edt1Input1.bottom, margin = 32.dp)
                start.linkTo(edt1Input1.start)
            }
        )
        OutlinedTextField(
            value = state.text3,
            maxLines = 5,
            onValueChange = { changeText3(it) },
            label = { Text("テキスト入力欄３") },
            modifier = Modifier.height(136.dp)
                .constrainAs(edt1Input3) {
                top.linkTo(txtTitle3.bottom, margin = 16.dp)
                start.linkTo(txtTitle3.start)
            }
        )
    }
}


/**
 * サブタイトル
 *
 * @param text テキスト
 * @param modifier レイアウト設定
 */
@Composable
fun SubTitleText(text: String, modifier: Modifier) {
    Box(modifier = modifier) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
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
fun PreviewTextGroupExtraScreen() {
    val texGroupViewModel = TextGroupExtraViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        TextGroupExtraScreen(texGroupViewModel)
    }
}
