package com.myapp.composesample.ui.left

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/**
 * 左画面
 *
 */
@Composable
fun FirstScreen(viewModel: FirstViewModel, navController: NavHostController) {

    // effect
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is FirstContract.Effect.NavigateToButtonGroupScreen -> {
                    navController.navigate(NavigationScreens.BUTTON_GROUP_SCREEN.route) {
                        popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is FirstContract.Effect.NavigateToLogicScreen -> {
                    navController.navigate(NavigationScreens.LOGIC_GROUP_SCREEN.route) {
                        popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is FirstContract.Effect.NavigateToTextGroupScreen -> {
                    navController.navigate(NavigationScreens.TEXT_GROUP_SCREEN.route) {
                        popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is FirstContract.Effect.NavigateToTextGroupExtraScreen -> {
                    navController.navigate(NavigationScreens.TEXT_GROUP_EXTRA_SCREEN.route) {
                        popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is FirstContract.Effect.NavigateToListGroupScreen -> {
                    navController.navigate(NavigationScreens.LIST_GROUP_SCREEN.route) {
                        popUpTo(NavigationScreens.FIRST_SCREEN.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }.collect()
    }

    // event
    val navigateToTextGroupScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToTextGroupScreen)
    }
    val navigateToTextGroupExtraScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToTextGroupExtraScreen)
    }
    val navigateToButtonGroupScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToButtonGroupScreen)
    }
    val navigateToLogicScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToLogicScreen)
    }
    val navigateToLListGroupScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToListGroupScreen)
    }
    // 画面描画
    Column(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayoutContent(
            navigateToTextGroupScreen,
            navigateToTextGroupExtraScreen,
            navigateToButtonGroupScreen,
            navigateToLogicScreen,
            navigateToLListGroupScreen
        )
    }
}

/**
 * コンテンツ
 *
 */
@Composable
fun ConstraintLayoutContent(
    navigateToTextGroupScreen: () -> Unit,
    navigateToTextGroupExtraScreen: () -> Unit,
    navigateToButtonGroupScreen: () -> Unit,
    navigateToLogicScreen: () -> Unit,
    navigateToLListGroupScreen: () -> Unit
) {
    ConstraintLayout {
        val (title, img, button1, button2, button3, button4, button5) = createRefs()

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
            onClick = { navigateToTextGroupScreen() },
            modifier = Modifier.constrainAs(button1) {
                start.linkTo(parent.start, margin = 8.dp)
                bottom.linkTo(parent.bottom, margin = 64.dp)
            }
        ) {
            Text(stringResource(id = R.string.btn_text_screen))
        }

        // テキストExtraScreenボタン
        Button(
            onClick = { navigateToTextGroupExtraScreen() },
            modifier = Modifier.constrainAs(button4) {
                start.linkTo(button1.start)
                top.linkTo(button1.bottom, margin = 16.dp)
            }
        ) {
            Text(stringResource(id = R.string.btn_text_extra_screen))
        }

        // ButtonScreenボタン
        Button(
            onClick = { navigateToButtonGroupScreen() },
            modifier = Modifier.constrainAs(button2) {
                start.linkTo(button1.end, margin = 16.dp)
                bottom.linkTo(button1.bottom)
            }
        ) {
            Text(stringResource(id = R.string.btn_button_screen))
        }

        // ロジック関連ボタン
        Button(
            onClick = { navigateToLogicScreen() },
            modifier = Modifier.constrainAs(button3) {
                start.linkTo(button2.end, margin = 16.dp)
                bottom.linkTo(button2.bottom)
            }
        ) {
            Text(stringResource(id = R.string.btn_logic_screen))
        }

        // リスト関連ボタン
        Button(
            onClick = { navigateToLListGroupScreen() },
            modifier = Modifier.constrainAs(button5) {
                top.linkTo(button4.bottom, margin = 16.dp)
                start.linkTo(button4.start)
            }
        ) {
            Text(stringResource(id = R.string.btn_list_screen))
        }
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
fun FirstScreenDemo() {
    val navController = rememberNavController()
    val viewModel = FirstViewModel()
    Scaffold(backgroundColor = Color(0xfff5f5f5)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            FirstScreen(viewModel, navController)
        }
    }
}
