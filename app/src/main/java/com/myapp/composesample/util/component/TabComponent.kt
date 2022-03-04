package com.myapp.composesample.util.component

// ========================================
// 　　　　　TabコンテンツComponent
// ========================================

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import com.myapp.composesample.ui.right.Tab1Screen
import com.myapp.composesample.ui.right.Tab2Screen
import com.myapp.composesample.ui.right.Tab3Screen


// ======================================== //
// 　　　　　Tabリスト                         //
// ======================================== //

/**
 * タブリスト（基底クラス）
 *
 * @property icon アイコン
 * @property title タイトル
 * @property screen 表示する画面
 */
abstract class TabItem(val icon: ImageVector, val title: String, val screen: @Composable () -> Unit)

/**
 * サンプル１タブ
 */
sealed class Sample1TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit) : TabItem(icon, title, screen){
    @ExperimentalPagerApi
    object Music : Sample1TabItem(Icons.Filled.Home, "Text Only", { Sample2TabComponent() })
    @ExperimentalPagerApi
    object Movies : Sample1TabItem(Icons.Filled.ShoppingCart, "Icon Only", { Sample3TabComponent() })
    @ExperimentalPagerApi
    object Books : Sample1TabItem(Icons.Filled.Settings, "Scroll", { Sample4TabComponent() })
}

/**
 * サンプル２タブ
 */
sealed class Sample2TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit) : TabItem(icon, title, screen){
    object Tab1 : Sample2TabItem(Icons.Filled.Home, "Home", { Tab1Screen() })
    object Tab2 : Sample2TabItem(Icons.Filled.ShoppingCart, "Shopp", { Tab2Screen() })
    object Tab3 : Sample2TabItem(Icons.Filled.Settings, "Setting", { Tab3Screen() })
}
/**
 * サンプル３タブ
 */
sealed class Sample3TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit) : TabItem(icon, title, screen){
    object Tab1 : Sample3TabItem(Icons.Filled.Home, "Home", { Tab1Screen() })
    object Tab2 : Sample3TabItem(Icons.Filled.ShoppingCart, "Shopp", { Tab2Screen() })
    object Tab3 : Sample3TabItem(Icons.Filled.Settings, "Setting", { Tab3Screen() })
}

/**
 * サンプル４タブ
 */
sealed class Sample4TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit) : TabItem(icon, title, screen){
    object Tab1 : Sample4TabItem(Icons.Filled.Home, "Tab1", { Tab1Screen() })
    object Tab2 : Sample4TabItem(Icons.Filled.ShoppingCart, "Tab2", { Tab2Screen() })
    object Tab3 : Sample4TabItem(Icons.Filled.Settings, "Tab3", { Tab3Screen() })
    object Tab4 : Sample4TabItem(Icons.Filled.Home, "Tab4", { Tab1Screen() })
    object Tab5 : Sample4TabItem(Icons.Filled.ShoppingCart, "Tab5", { Tab2Screen() })
    object Tab6 : Sample4TabItem(Icons.Filled.Settings, "Tab6", { Tab3Screen() })
}
// ========================================  //
// 　　　　　公開用タブ画面Component             //
// ======================================== //

/**
 * サンプルタブ画面Component
 *
 */
@ExperimentalPagerApi
@Composable
fun Sample1TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = listOf(Sample1TabItem.Music, Sample1TabItem.Movies, Sample1TabItem.Books)

    // レイアウト
    Column {
        TextIconTabBar(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}
/**
 * サンプルタブ画面Component
 *
 */
@ExperimentalPagerApi
@Composable
fun Sample2TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = listOf(
        Sample2TabItem.Tab1,
        Sample2TabItem.Tab2,
        Sample2TabItem.Tab3
    )

    // レイアウト
    Column {
        TextTabBar(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

/**
 * サンプルタブ画面Component
 *
 */
@ExperimentalPagerApi
@Composable
fun Sample3TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = listOf(
        Sample3TabItem.Tab1,
        Sample3TabItem.Tab2,
        Sample3TabItem.Tab3
    )

    // レイアウト
    Column {
        IconTabBar(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

/**
 * サンプルタブ画面Component
 *
 */
@ExperimentalPagerApi
@Composable
fun Sample4TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = listOf(
        Sample4TabItem.Tab1,
        Sample4TabItem.Tab2,
        Sample4TabItem.Tab3,
        Sample4TabItem.Tab4,
        Sample4TabItem.Tab5,
        Sample4TabItem.Tab6
    )

    // レイアウト
    Column {
        ScrollableTabBar(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}


// ======================================== //
// 　　　　　内部用Util Tab Component          //
// ======================================== //

/**
 * タブバー
 *
 * @param tabs タブリスト
 * @param pagerState pager
 */
@ExperimentalPagerApi
@Composable
private fun TextTabBar(tabs: List<TabItem>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(text = tab.title) }
            )
        }
    }
}

/**
 * Text&Iconタブバー
 *
 * @param tabs タブリスト
 * @param pagerState pager
 */
@ExperimentalPagerApi
@Composable
private fun TextIconTabBar(tabs: List<TabItem>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            // Tab：画像とテキストが縦で並ぶ
            // LeadingIconTab：画像とテキストが横で並ぶ
            LeadingIconTab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(text = tab.title) },
                icon = { Icon(imageVector = tab.icon, contentDescription = null) }
            )
        }
    }
}

/**
 * Iconタブバー
 *
 * @param tabs タブリスト
 * @param pagerState pager
 */
@ExperimentalPagerApi
@Composable
private fun IconTabBar(tabs: List<TabItem>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = { Icon(imageVector = tab.icon, contentDescription = null) }
            )
        }
    }
}

/**
 * スクロールタブバー
 *
 * @param tabs タブリスト
 * @param pagerState pager
 */
@ExperimentalPagerApi
@Composable
private fun ScrollableTabBar(tabs: List<TabItem>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = { Text(text = tab.title) }
            )
        }
    }
}

/**
 * タブコンテンツ
 *
 * @param tabs タブリスト
 * @param pagerState pager
 */
@ExperimentalPagerApi
@Composable
private fun TabsContent(tabs: List<TabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}
