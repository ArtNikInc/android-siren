package com.adtarassov.siren.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adtarassov.siren.ui.utils.Screens.MAIN_SCREEN
import com.adtarassov.siren.ui.utils.Screens.SPLASH_SCREEN
import com.adtarassov.siren.ui.screens.base.MainScreen
import com.adtarassov.siren.ui.screens.base.SplashScreen
import com.adtarassov.siren.ui.utils.Screens.ROOT

sealed class SirenNavRoute(val route: String) {
  object Main : SirenNavRoute(MAIN_SCREEN)
  object Splash : SirenNavRoute(SPLASH_SCREEN)
}

@Composable
fun SirenNavHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    route = ROOT,
    startDestination = SirenNavRoute.Main.route
  ) {
     composable(SirenNavRoute.Splash.route) {
      SplashScreen(navController = navController)
    }
    composable(SirenNavRoute.Main.route) {
      MainScreen()
    }
  }
}