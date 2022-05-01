package com.myapp.composesample.ui.center

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.myapp.composesample.ui.NavigationScreens

/**
 * 中央画面
 *
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SecondScreen(navController: NavHostController) {
    Scaffold(
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column() {
                Button(
                    onClick = { navController.navigate(NavigationScreens.RESPONSIBLE_SCREEN.route) }
                ) {
                    Text(text = "レスポンシブルレイアウト")
                }

                Button(
                    onClick = { navController.navigate(NavigationScreens.ANIMATION_SCREEN.route) }
                ) {
                    Text(text = "アニメーション関連")
                }
                Button(
                    onClick = { navController.navigate(NavigationScreens.LIST_SCREEN.route) }
                ) {
                    Text(text = "リスト表示関連")
                }
                Button(
                    onClick = { navController.navigate(NavigationScreens.CHART_SCREEN.route) }
                ) {
                    Text(text = "グラフ表示")
                }
            }
        }
    }
}