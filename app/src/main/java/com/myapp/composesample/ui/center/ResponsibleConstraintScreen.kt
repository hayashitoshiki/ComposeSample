package com.myapp.composesample.ui.center

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.myapp.composesample.util.component.keyboardAsState

/**
 * キーボードや端末の画面サイズによってレスポンシブルにレイアウトが変わる画面
 *
 * レスポンシブル化するときは下記を追加
 * 　・build.gradle・・・implementation "com.google.accompanist:accompanist-insets:$accompanist_version"
 *  ・レイアウトの最上部・・・ProvideWindowInsets(windowInsetsAnimationsEnabled = true)
 *  ・AndroidManifestの対象のActivity・・・android:windowSoftInputMode="adjustResize"
 *
 */
@Composable
fun ResponsibleConstraintScreen(navController: NavHostController) {
    ResponsibleConstraintContent(navController)
}

/**
 * 画面定義
 *
 */
@Composable
fun ResponsibleConstraintContent(navController: NavHostController) {
    val isDialog = remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    val text = remember { mutableStateOf("") }

    // 画面
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
    ) {
        val (topContent, mainContent, bottomContent) = createRefs()
        // 上部
        Text(
            text = "動的レイアウト変更ダイアログ",
            modifier = Modifier.constrainAs(topContent) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }
        )

        // 中間
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            modifier = Modifier.constrainAs(mainContent) {
                top.linkTo(topContent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomContent.top)
                height = Dimension.fillToConstraints
            }
        )

        // 下部
        Button(
            onClick = {
                isDialog.value = true
            },
            modifier = Modifier.constrainAs(bottomContent) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(text = "ダイアログ表示")
        }
        if (isDialog.value) {
            ResponsibleConstraintDialog { isDialog.value = false}
        }
    }
}

/**
 *  キーボードや端末の画面サイズによってレスポンシブルにレイアウトが変わるダイアログ
 *
 *  レイアウトをBoxWithConstraintsで囲んで、
 *  maxHeightとkeyboardAsStateのキーボードの大きさから計算して、
 *  レイアウトのheightへ当て込む
 *
 * @param onClick クリックアクション
 */
@Composable
fun ResponsibleConstraintDialog(onClick: () -> Unit) {
    // キーボード検知
    val isKeyboardOpen by keyboardAsState()

    androidx.compose.ui.window.Dialog(onDismissRequest = {}) {
        BoxWithConstraints( modifier = Modifier.fillMaxSize()) {
            Surface(modifier = Modifier.padding(top = 30.dp, bottom = 30.dp, start = 16.dp, end = 16.dp)) {

                val contentHeight = maxHeight - isKeyboardOpen.height - 50.dp
                val focusManager = LocalFocusManager.current
                val text = remember { mutableStateOf("") }

                // 画面
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .height(contentHeight)
                        .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) }
                ) {
                    val (topContent, mainContent, bottomContent) = createRefs()
                    // 上部
                    Text(
                        text = "動的レイアウト変更ダイアログ",
                        modifier = Modifier
                            .height(40.dp)
                            .constrainAs(topContent) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                    // 中間
                    TextField(
                        value = text.value,
                        onValueChange = { text.value = it },
                        modifier = Modifier
                            .constrainAs(mainContent) {
                                top.linkTo(topContent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(bottomContent.top)
                                height = Dimension.fillToConstraints
                            }
                    )

                    // 下部
                    Button(
                        onClick = { onClick() },
                        modifier = Modifier
                            .height(40.dp)
                            .constrainAs(bottomContent) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        Text(text = "閉じる")
                    }
                }
            }
        }
    }
}