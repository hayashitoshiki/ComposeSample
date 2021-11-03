package com.myapp.composesample.ui

import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myapp.composesample.R
import com.myapp.composesample.ui.center.SecondScreen
import com.myapp.composesample.ui.left.*
import com.myapp.composesample.ui.right.ThirdScreen

/**
 * 画面定義
 *
 * @property group Bottomタブ
 * @property route 遷移パス
 * @property resourceId ナビゲーションタイトル
 * @property imgRes ナビゲーションアイコン
 */
enum class NavigationScreens(
    val group: Group,
    val route: String,
    @StringRes val resourceId: Int,
    val imgRes: ImageVector
) {

    FIRST_SCREEN(
        Group.LEFT,
        "first_fragment_navigate",
        R.string.first_nav,
        Icons.Filled.Home
    ),
    SECOND_SCREEN(
        Group.CENTER,
        "second_fragment_navigate",
        R.string.second_nav,
        Icons.Filled.Info
    ),
    THIRD_SCREEN(
        Group.RIGHT,
        "third_fragment_navigate",
        R.string.third_nav,
        Icons.Filled.Email
    ),
    TEXT_GROUP_SCREEN(
        Group.LEFT,
        "text_group_fragment_navigate",
        R.string.third_nav,
        Icons.Filled.Email
    ),
    TEXT_GROUP_EXTRA_SCREEN(
        Group.LEFT,
        "button_group_extra_fragment_navigate",
        R.string.button_nav,
        Icons.Filled.Email
    ),
    BUTTON_GROUP_SCREEN(
        Group.LEFT,
        "button_group_fragment_navigate",
        R.string.button_nav,
        Icons.Filled.Email
    ),
    LOGIC_GROUP_SCREEN(
        Group.LEFT,
        "logic_group_fragment_navigate",
        R.string.button_nav,
        Icons.Filled.Email
    );

    enum class Group {
        LEFT, CENTER, RIGHT;
    }


    companion object {
        /**
         * 遷移パスからタブグループ検索
         *
         * @param route 遷移パス
         * @return タブグループ（存在しない遷移パスの場合はNullを返す)
         */
        fun findGroupByRoute(route: String?): Group? {
            return values()
                .filter { it.route == route }
                .map { it.group }
                .firstOrNull()
        }
    }

}

/**
 * NavigationHost
 *
 * @param navController ナビゲーションAPI
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    logicViewModel: LogicGroupViewModel,
    firstViewModel: FirstViewModel,
    textViewModel: TextGroupViewModel,
    buttonViewModel: ButtonGroupViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreens.FIRST_SCREEN.route
    ) {
        composable(route = NavigationScreens.FIRST_SCREEN.route) { FirstScreen(firstViewModel, navController) }
        composable(route = NavigationScreens.SECOND_SCREEN.route) { SecondScreen() }
        composable(route = NavigationScreens.THIRD_SCREEN.route) { ThirdScreen() }
        composable(route = NavigationScreens.TEXT_GROUP_SCREEN.route) { TextGroupScreen(textViewModel) }
        composable(route = NavigationScreens.TEXT_GROUP_EXTRA_SCREEN.route) { TextGroupExtraScreen(viewModel()) }
        composable(route = NavigationScreens.BUTTON_GROUP_SCREEN.route) { ButtonGroupScreen(buttonViewModel) }
        composable(route = NavigationScreens.LOGIC_GROUP_SCREEN.route) { LogicGroupScreen(logicViewModel) }
    }
}

/**
 * BottomNavigation
 *
 * @param navController ナビゲーションAPI
 * @param items navigationタブリスト
 */
@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    items: List<NavigationScreens>
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.imgRes, contentDescription = null) },
                label = { Text(text = stringResource(id = screen.resourceId)) },
                selected = NavigationScreens.findGroupByRoute(currentRoute) == screen.group,
                alwaysShowLabel = true,
                onClick = {
                    if (NavigationScreens.findGroupByRoute(currentRoute) != screen.group) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = false
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