package com.adtarassov.siren.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adtarassov.siren.ui.Screens.MAIN_SCREEN
import com.adtarassov.siren.ui.Screens.FEED_SCREEN
import com.adtarassov.siren.ui.Screens.SPLASH_SCREEN
import com.adtarassov.siren.ui.screens.MainScreen
import com.adtarassov.siren.ui.screens.SplashScreen

sealed class NavRoute(val route: String) {
  object Main : NavRoute(MAIN_SCREEN)
  object Splash : NavRoute(SPLASH_SCREEN)
  object Feed : NavRoute(FEED_SCREEN)
}

@Composable
fun SirenNavHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = NavRoute.Splash.route
  ) {
    composable(NavRoute.Splash.route) {
      SplashScreen(navController = navController)
    }
    composable(NavRoute.Main.route) {
      MainScreen()
    }
    composable(NavRoute.Feed.route) {

    }
  }
}