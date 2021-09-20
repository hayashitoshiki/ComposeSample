package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.myapp.composesample.R
import com.myapp.composesample.ui.NavigationScreens

/**
 * 左画面
 *
 */
@Composable
fun FirstScreen(navController: NavHostController) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ConstraintLayoutContent(navController)
        }
}

/**
 * コンテンツ
 *
 */
@Composable
fun ConstraintLayoutContent(navController: NavHostController) {
    ConstraintLayout {
        val (title, img, button1, button2, button3) = createRefs()

        // タイトル
        Text(
            text = stringResource(id = R.string.title_first_screen),
            fontSize = 24.sp,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 128.dp)
                start.linkTo(parent.start, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            }
        )

        // メイン画像
        Image(
            painter = painterResource(R.drawable.ic_phone_android_black_48),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
                .constrainAs(img) {
                    top.linkTo(title.bottom, margin = 32.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(button1.top, margin = 64.dp)
                }
        )

        // テキストScreenボタン
        Button(
            onClick = {
                navController.navigate(NavigationScreens.TEXT_GROUP_SCREEN.route){
                    popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.constrainAs(button1) {
                start.linkTo(parent.start, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 64.dp)
            }
        ) {
            Text(stringResource(id = R.string.btn_text_screen))
        }

        // ButtonScreenボタン
        Button(
            onClick = {
                navController.navigate(NavigationScreens.BUTTON_GROUP_SCREEN.route){
                    popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.constrainAs(button2) {
                start.linkTo(button1.end, margin = 16.dp)
                bottom.linkTo(button1.bottom)
            }
        ) {
            Text(stringResource(id = R.string.btn_button_screen))
        }

        // ロジック関連ボタン
        Button(
            onClick = {
                navController.navigate(NavigationScreens.LOGIC_GROUP_SCREEN.route){
                    popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
                      },
            modifier = Modifier.constrainAs(button3) {
                start.linkTo(button2.end, margin = 16.dp)
                bottom.linkTo(button2.bottom)
            }
        ) {
            Text(stringResource(id = R.string.btn_logic_screen))
        }

    }
}


@Preview(
    showBackground = true,
    name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun FirstScreenDemo() {
    val navController = rememberNavController()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ConstraintLayoutContent(navController)
        }
    }
}
