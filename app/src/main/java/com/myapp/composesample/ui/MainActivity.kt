package com.myapp.composesample.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.myapp.composesample.R
import com.myapp.composesample.ui.center.SecondScreen
import com.myapp.composesample.ui.left.FirstScreen
import com.myapp.composesample.ui.left.TextGroupScreen
import com.myapp.composesample.ui.right.ThirdScreen
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
            BottomNavigationScreens.FirstScreen,
            BottomNavigationScreens.SecondScreen,
            BottomNavigationScreens.ThirdScreen
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

/**
 * BottomNavigationタブ定義
 *
 * @property route 接続パス
 * @property resourceId ナビゲーションタイトル
 * @property imgRes ナビゲーションアイコン
 */
sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val imgRes: ImageVector) {

    object FirstScreen : BottomNavigationScreens(
        "first_fragment_navigate",
        R.string.first_nav,
        Icons.Filled.Home)

    object SecondScreen : BottomNavigationScreens(
        "second_fragment_navigate",
        R.string.second_nav,
        Icons.Filled.Info)

    object ThirdScreen : BottomNavigationScreens(
        "third_fragment_navigate",
        R.string.third_nav,
        Icons.Filled.Email)

    object TextGroupScreen : BottomNavigationScreens(
        "text_group_fragment_navigate",
        R.string.third_nav,
        Icons.Filled.Email)
}

/**
 * NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.FirstScreen.route
    ) {
        composable(route = BottomNavigationScreens.FirstScreen.route) { FirstScreen(navController) }
        composable(route = BottomNavigationScreens.SecondScreen.route) { SecondScreen() }
        composable(route = BottomNavigationScreens.ThirdScreen.route) { ThirdScreen() }
        composable(route = BottomNavigationScreens. TextGroupScreen.route) { TextGroupScreen() }
    }
}

/**
 * BottomNavigation
 *
 * @param navController ナビゲーションAPI
 * @param items navigationタブリスト
 */
@Composable
private fun AppBottomNavigation(
    navController: NavHostController,
    items: List<BottomNavigationScreens>
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.imgRes,contentDescription = null) },
                label = { Text( text = stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabel = true,
                onClick = {
                   if (currentRoute != screen.route) {
                        navController.navigate(screen.route){
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
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
