package com.myapp.composesample.ui.center

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
@Composable
fun SecondScreen(navController: NavHostController) {
    Scaffold(
        backgroundColor = Color(0xfff5f5f5)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Button(
                onClick = { navController.navigate(NavigationScreens.RESPONSIBLE_SCREEN.route) }
            ){
                Text(text = "レスポンシブルレイアウト")
            }
        }
    }
}