package com.myapp.composesample.ui.left

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
        Log.d("Lifecycle", "LaunchedEffect")
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
                is FirstContract.Effect.NavigateToTextScrollerScreen -> {
                    navController.navigate(NavigationScreens.TEXT_SCROLLER_SCREEN.route) {
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

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val observer = remember {
        LifecycleEventObserver {_, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> { Log.d("Lifecycle", ">>>>>>>>>>>>ON_CREATE")}
                Lifecycle.Event.ON_START -> { Log.d("Lifecycle", ">>>>>>>>>>>>>ON_START")}
                Lifecycle.Event.ON_RESUME -> { Log.d("Lifecycle", ">>>>>>>>>>>>>>ON_RESUME")}
                Lifecycle.Event.ON_PAUSE -> { Log.d("Lifecycle", "<<<<<<<<<<<<<<ON_PAUSE")}
                Lifecycle.Event.ON_STOP -> { Log.d("Lifecycle", "<<<<<<<<<<<<<ON_STOP")}
                Lifecycle.Event.ON_DESTROY -> { Log.d("Lifecycle", "<<<<<<<<<<<<ON_DESTROY")}
                Lifecycle.Event.ON_ANY -> { Log.d("Lifecycle", "ON_ANY")}
            }
        }
    }
    DisposableEffect(Unit) {
        lifecycle.addObserver(observer)
        onDispose {
            Log.d("Lifecycle", "DisposableEffect")
            lifecycle.removeObserver(observer)
        }
    }


    // event
    val navigateToTextGroupScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToTextGroupScreen)
    }
    val navigateToTextGroupExtraScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToTextGroupExtraScreen)
    }
    val navigateToTextScrollerScreen: () -> Unit = {
        viewModel.setEvent(FirstContract.Event.NavigateToTextScrollerScreen)
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
    ConstraintLayoutContent(
        navigateToTextGroupScreen,
        navigateToTextGroupExtraScreen,
        navigateToTextScrollerScreen,
        navigateToButtonGroupScreen,
        navigateToLogicScreen,
        navigateToLListGroupScreen
    )
}

/**
 * コンテンツ
 *
 */
@Composable
fun ConstraintLayoutContent(
    navigateToTextGroupScreen: () -> Unit,
    navigateToTextGroupExtraScreen: () -> Unit,
    navigateToTextScrollerScreen: () -> Unit,
    navigateToButtonGroupScreen: () -> Unit,
    navigateToLogicScreen: () -> Unit,
    navigateToLListGroupScreen: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        val (title, img, buttonArea) = createRefs()

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
                }
        )
        Column(modifier = Modifier.constrainAs(buttonArea) {
            top.linkTo(img.bottom, margin = 32.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, margin = 8.dp)
        }) {

            // テキストScreenボタン
            Button(
                onClick = { navigateToTextGroupScreen() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.btn_text_screen))
            }

            // テキストExtraScreenボタン
            Button(
                onClick = { navigateToTextGroupExtraScreen() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.btn_text_extra_screen))
            }

            // テキストExtraScreenボタン
            Button(
                onClick = { navigateToTextScrollerScreen() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.btn_text_scroll_screen))
            }

            // ButtonScreenボタン
            Button(
                onClick = { navigateToButtonGroupScreen() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.btn_button_screen))
            }

            // ロジック関連ボタン
            Button(
                onClick = { navigateToLogicScreen() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.btn_logic_screen))
            }

            // リスト関連ボタン
            Button(
                onClick = { navigateToLListGroupScreen() },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(stringResource(id = R.string.btn_list_screen))
            }
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
