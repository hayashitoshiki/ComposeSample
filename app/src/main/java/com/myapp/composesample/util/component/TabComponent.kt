package com.myapp.composesample.util.component

// ========================================
// 　　　　　TabコンテンツComponent
// ========================================

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import com.myapp.composesample.ui.right.Tab1Screen
import com.myapp.composesample.ui.right.Tab2Screen
import com.myapp.composesample.ui.right.Tab3Screen
import com.myapp.composesample.util.Extension.SealedClassEnumExtension
import com.myapp.composesample.util.Extension.SealedClassEnumWithName
import com.myapp.composesample.util.Extension.values


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
sealed class Sample1TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit)
    : TabItem(icon, title, screen), SealedClassEnumWithName {
    @ExperimentalPagerApi
    object TextIcon : Sample1TabItem(Icons.Filled.Home, "Text & Icon", { Sample2TabComponent() })
    @ExperimentalPagerApi
    object Text : Sample1TabItem(Icons.Filled.Home, "Text Only", { Sample3TabComponent() })
    @ExperimentalPagerApi
    object Icon : Sample1TabItem(Icons.Filled.ShoppingCart, "Icon Only", { Sample4TabComponent() })
    @ExperimentalPagerApi
    object Scroll : Sample1TabItem(Icons.Filled.Settings, "Scroll", { Sample5TabComponent() })

    companion object : SealedClassEnumExtension<Sample1TabItem>
}

/**
 * サンプル２タブ
 */
sealed class Sample2TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit)
    : TabItem(icon, title, screen), SealedClassEnumWithName {
    object Tab1 : Sample2TabItem(Icons.Filled.Home, "Home", { Tab1Screen() })
    object Tab2 : Sample2TabItem(Icons.Filled.ShoppingCart, "Shopp", { Tab2Screen() })
    object Tab3 : Sample2TabItem(Icons.Filled.Settings, "Setting", { Tab3Screen() })

    companion object : SealedClassEnumExtension<Sample2TabItem>
}

/**
 * サンプル３タブ
 */
sealed class Sample3TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit)
    : TabItem(icon, title, screen), SealedClassEnumWithName {
    object Tab1 : Sample3TabItem(Icons.Filled.Home, "Home", { Tab1Screen() })
    object Tab2 : Sample3TabItem(Icons.Filled.ShoppingCart, "Shopp", { Tab2Screen() })
    object Tab3 : Sample3TabItem(Icons.Filled.Settings, "Setting", { Tab3Screen() })

    companion object : SealedClassEnumExtension<Sample3TabItem>
}

/**
 * サンプル４タブ
 */
sealed class Sample4TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit)
    : TabItem(icon, title, screen), SealedClassEnumWithName {
    object Tab1 : Sample4TabItem(Icons.Filled.Home, "Tab1", { Tab1Screen() })
    object Tab2 : Sample4TabItem(Icons.Filled.ShoppingCart, "Tab2", { Tab2Screen() })
    object Tab3 : Sample4TabItem(Icons.Filled.Settings, "Tab3", { Tab3Screen() })

    companion object : SealedClassEnumExtension<Sample4TabItem>
}

/**
 * サンプル５タブ
 */
sealed class Sample5TabItem(icon: ImageVector, title: String, screen: @Composable () -> Unit)
    : TabItem(icon, title, screen), SealedClassEnumWithName {
    object Tab1 : Sample5TabItem(Icons.Filled.Home, "Tab1", { Tab1Screen() })
    object Tab2 : Sample5TabItem(Icons.Filled.ShoppingCart, "Tab2", { Tab2Screen() })
    object Tab3 : Sample5TabItem(Icons.Filled.Settings, "Tab3", { Tab3Screen() })
    object Tab4 : Sample5TabItem(Icons.Filled.Home, "Tab4", { Tab1Screen() })
    object Tab5 : Sample5TabItem(Icons.Filled.ShoppingCart, "Tab5", { Tab2Screen() })
    object Tab6 : Sample5TabItem(Icons.Filled.Settings, "Tab6", { Tab3Screen() })

    companion object : SealedClassEnumExtension<Sample5TabItem>
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
    val tabs = Sample1TabItem.values()

    // レイアウト
    Column {
        CustomTabBar(tabs = tabs, pagerState = pagerState)
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
    val tabs = Sample2TabItem.values()

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
fun Sample3TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = Sample3TabItem.values()

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
fun Sample4TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = Sample4TabItem.values()

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
fun Sample5TabComponent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val tabs = Sample5TabItem.values()

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
 * Textタブバー
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
 * カスタムレイアウトタブバー
 *
 * @param tabs タブリスト
 * @param pagerState pager
 */
@ExperimentalPagerApi
@Composable
private fun CustomTabBar(tabs: List<TabItem>, pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.Transparent,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions))
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = pagerState.currentPage == index
            val tabColor = if (isSelected) { Color.Red } else { Color.Transparent }
            val roundedCornerShape = RoundedCornerShape(
                topStart = if (index == 0){ 10.dp } else { 0.dp },
                topEnd = if (index == tabs.size -1){ 10.dp } else { 0.dp }
            )

            Tab(
                selected = isSelected,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            ) {
                // この中でタブのレイアウトを作成
                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth()
                        .clip(roundedCornerShape)
                        .background(color = tabColor)
                        .border(
                            width = 1.dp,
                            color = Color.DarkGray,
                            shape = roundedCornerShape
                        )
                ) {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
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
