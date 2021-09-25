package com.myapp.composesample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.myapp.composesample.util.theme.ComposeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                ComposeBaseApp()
            }
        }
    }
}

/**
 * ベース画面
 *
 */
@Preview
@Composable
fun ComposeBaseApp() {
    ComposeSampleTheme {
        val navController = rememberNavController()
        val bottomNavigationItems = listOf(
            NavigationScreens.FIRST_SCREEN,
            NavigationScreens.SECOND_SCREEN,
            NavigationScreens.THIRD_SCREEN
        )
        Scaffold(
            bottomBar = {
                AppBottomNavigation(navController, bottomNavigationItems)
            },
            backgroundColor = Color(0xfff5f5f5)
        ) {
            AppNavHost(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        ComposeBaseApp()
    }
}
