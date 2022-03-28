package com.myapp.composesample.ui

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myapp.composesample.R
import com.myapp.composesample.ui.center.AnimationScreen
import com.myapp.composesample.ui.center.ResponsibleConstraintScreen
import com.myapp.composesample.ui.center.SecondScreen
import com.myapp.composesample.ui.left.*
import com.myapp.composesample.ui.right.ThirdScreen

/**
 * 画面定義
 *
 * @property group Bottomタブ
 * @property route 遷移パス
 * @property resourceId 画面タイトル
 */
enum class NavigationScreens(
    val group: Group,
    val route: String,
    @StringRes val resourceId: Int
) {

    // 左タブ
    FIRST_SCREEN(Group.LEFT, "first_fragment_navigate", R.string.first_nav),
    TEXT_GROUP_SCREEN(Group.LEFT, "text_group_fragment_navigate", R.string.third_nav),
    TEXT_GROUP_EXTRA_SCREEN(Group.LEFT, "button_group_extra_fragment_navigate", R.string.button_nav),
    TEXT_SCROLLER_SCREEN(Group.LEFT, "text_scroller_fragment_navigate", R.string.button_nav),
    BUTTON_GROUP_SCREEN(Group.LEFT, "button_group_fragment_navigate", R.string.button_nav),
    LOGIC_GROUP_SCREEN(Group.LEFT, "logic_group_fragment_navigate", R.string.button_nav),
    LIST_GROUP_SCREEN(Group.LEFT, "list_group_extra_fragment_navigate", R.string.button_nav),

    // 中央タブ
    SECOND_SCREEN(Group.CENTER, "second_fragment_navigate", R.string.second_nav),
    RESPONSIBLE_SCREEN(Group.CENTER, "responsible_fragment_navigate", R.string.second_nav),
    ANIMATION_SCREEN(Group.CENTER, "animation_fragment_navigate", R.string.second_nav),

    // 右タブ
    THIRD_SCREEN(Group.RIGHT, "third_fragment_navigate", R.string.third_nav);

    /**
     * ナビゲーションタブ定義
     *
     * @property title タブタイトル
     * @property imgRes タブアイコン
     * @property route タブの先頭のroute
     */
    enum class Group(@StringRes val title: Int, val imgRes: ImageVector, val route: String) {
        LEFT(
            R.string.bottom_bar_title_left,
            Icons.Filled.Email,
            "first_fragment_navigate"
        ),
        CENTER(
            R.string.bottom_bar_title_center,
            Icons.Filled.Info,
        "second_fragment_navigate"
        ),
        RIGHT(
            R.string.bottom_bar_title_right,
            Icons.Filled.Home,
        "third_fragment_navigate"
        );
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
 * NavigationHost 画面遷移定義
 *
 * @param navController ナビゲーションAPI
 */
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
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
        // 左タブ
        composable(route = NavigationScreens.FIRST_SCREEN.route) { FirstScreen(firstViewModel, navController) }
        composable(route = NavigationScreens.THIRD_SCREEN.route) { ThirdScreen() }
        composable(route = NavigationScreens.TEXT_GROUP_SCREEN.route) { TextGroupScreen(textViewModel) }
        composable(route = NavigationScreens.TEXT_GROUP_EXTRA_SCREEN.route) { TextGroupExtraScreen(viewModel()) }
        composable(route = NavigationScreens.TEXT_SCROLLER_SCREEN.route) { TextScrollerScreen() }
        composable(route = NavigationScreens.BUTTON_GROUP_SCREEN.route) { ButtonGroupScreen(buttonViewModel) }
        composable(route = NavigationScreens.LOGIC_GROUP_SCREEN.route) { LogicGroupScreen(logicViewModel) }
        composable(route = NavigationScreens.LIST_GROUP_SCREEN.route) { ListGroupScreen(viewModel()) }

        // 中央タブ
        composable(route = NavigationScreens.SECOND_SCREEN.route) { SecondScreen(navController) }
        composable(route = NavigationScreens.RESPONSIBLE_SCREEN.route) { ResponsibleConstraintScreen(navController) }
        composable(route = NavigationScreens.ANIMATION_SCREEN.route) { AnimationScreen(navController) }
    }
}
