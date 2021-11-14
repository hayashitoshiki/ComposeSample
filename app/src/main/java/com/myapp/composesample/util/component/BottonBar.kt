package com.myapp.composesample.util.component

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myapp.composesample.ui.NavigationScreens

/**
 * Bottom Bar
 *
 * @param navController ナビゲーションAPI
 */
@Composable
fun AppBottomNavigation(navController: NavHostController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavigationScreens.Group.values().forEach { group ->
            BottomNavigationItem(
                icon = { Icon(group.imgRes, contentDescription = null) },
                label = { Text(text = stringResource(id = group.title)) },
                selected = NavigationScreens.findGroupByRoute(currentRoute) == group,
                alwaysShowLabel = true,
                onClick = {
                    if (NavigationScreens.findGroupByRoute(currentRoute) != group) {
                        navController.navigate(group.route) {
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