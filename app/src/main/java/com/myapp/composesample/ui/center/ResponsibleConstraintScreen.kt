package com.myapp.composesample.ui.center

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.myapp.composesample.util.component.AppBottomNavigation

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
    Scaffold(
        backgroundColor = Color(0xfff5f5f5),
        bottomBar = { AppBottomNavigation(navController) }
    ) { paddingValues ->
        // レスポンシブル化
        ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {

            val focusManager = LocalFocusManager.current
            val text = remember { mutableStateOf("") }

            // 画面
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
                    onClick = { },
                    modifier = Modifier.constrainAs(bottomContent) {
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
