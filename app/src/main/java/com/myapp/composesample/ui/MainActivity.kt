package com.myapp.composesample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myapp.composesample.ui.left.*
import com.myapp.composesample.util.component.AppBottomNavigation
import com.myapp.composesample.util.theme.ComposeSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val logicViewModel: LogicGroupViewModel by viewModels()
    private val firstViewModel: FirstViewModel by viewModels()
    private val textViewModel: TextGroupViewModel by viewModels()
    private val buttonViewModel: ButtonGroupViewModel by viewModels()

    @ExperimentalPagerApi
    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
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
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ComposeBaseApp(
    logicViewModel: LogicGroupViewModel,
    firstViewModel: FirstViewModel,
    textViewModel: TextGroupViewModel,
    buttonViewModel: ButtonGroupViewModel
) {
    ComposeSampleTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { AppBottomNavigation(navController) },
            backgroundColor = Color(0xfff5f5f5)
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
}

@Preview(showBackground = true)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun DefaultPreview() {
    ComposeSampleTheme {
        MainActivity()
    }
}
