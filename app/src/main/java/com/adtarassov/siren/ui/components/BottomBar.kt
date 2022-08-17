package com.adtarassov.siren.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.adtarassov.siren.ui.theme.SirenTheme
import com.adtarassov.siren.ui.utils.BottomBarScreen

val bottomBarScreens = listOf(
  BottomBarScreen.Feed,
  BottomBarScreen.Settings,
  BottomBarScreen.Profile,
)

@Composable
fun BottomBar(navController: NavHostController) {
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination
  BottomNavigation(
    backgroundColor = SirenTheme.colors.bgMain,
  ) {
    bottomBarScreens.forEach { screen ->
      AddItem(
        screen = screen,
        currentDestination = currentDestination,
        navController = navController
      )
    }
  }
}

@Composable
fun RowScope.AddItem(
  screen: BottomBarScreen,
  currentDestination: NavDestination?,
  navController: NavHostController,
) {
  BottomNavigationItem(
    label = {
      Text(
        color = SirenTheme.colors.mainText,
        text = stringResource(screen.titleId)
      )
    },
    selectedContentColor = SirenTheme.colors.mainText,
    icon = {
      Icon(
        imageVector = screen.icon,
        contentDescription = "Navigation Icon"
      )
    },
    selected = currentDestination?.hierarchy?.any {
      it.route?.startsWith(screen.route) == true
    } == true,
    unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
    onClick = {
      navController.navigate(screen.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(navController.graph.findStartDestination().id) {
          saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
      }
    }
  )
}