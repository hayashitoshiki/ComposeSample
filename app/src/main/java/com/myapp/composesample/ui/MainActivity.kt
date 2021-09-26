package com.myapp.composesample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.myapp.composesample.ui.left.ButtonGroupViewModel
import com.myapp.composesample.ui.left.FirstViewModel
import com.myapp.composesample.ui.left.LogicGroupViewModel
import com.myapp.composesample.ui.left.TextGroupViewModel
import com.myapp.composesample.util.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val logicViewModel: LogicGroupViewModel by viewModels()
    private val firstViewModel: FirstViewModel by viewModels()
    private val textViewModel: TextGroupViewModel by viewModels()
    private val buttonViewModel: ButtonGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeSampleTheme {
                ComposeBaseApp(
                    logicViewModel,
                    firstViewModel,
                    textViewModel,
                    buttonViewModel
                )
            }
        }
    }
}

/**
 * ベース画面
 *
 */
@Composable
fun ComposeBaseApp(
    logicViewModel: LogicGroupViewModel,
    firstViewModel: FirstViewModel,
    textViewModel: TextGroupViewModel,
    buttonViewModel: ButtonGroupViewModel
) {
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
            AppNavHost(
                navController = navController,
                logicViewModel,
                firstViewModel,
                textViewModel,
                buttonViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        MainActivity()
    }
}
