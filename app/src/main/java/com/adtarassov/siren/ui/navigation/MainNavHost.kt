package com.adtarassov.siren.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.adtarassov.siren.ui.screens.FeedScreen
import com.adtarassov.siren.ui.utils.BottomBarScreen
import com.adtarassov.siren.ui.utils.Screens.MAIN_SCREEN
import com.adtarassov.siren.ui.screens.FeedScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainNavHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    route = MAIN_SCREEN,
    startDestination = BottomBarScreen.Feed.route
  ) {
    composable(route = BottomBarScreen.Feed.route) {
      val viewModel: FeedScreenViewModel = hiltViewModel()
      FeedScreen(navController = navController, viewModel = viewModel)
    }
    composable(route = BottomBarScreen.Profile.route) {
      Text(text = "title2")
    }
    composable(route = BottomBarScreen.Settings.route) {
      Text(text = "title3")
    }
  }
}

//fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
//  navigation(
//    route = Graph.DETAILS,
//    startDestination = DetailsScreen.Information.route
//  ) {
//    composable(route = DetailsScreen.Information.route) {
//      ScreenContent(name = DetailsScreen.Information.route) {
//        navController.navigate(DetailsScreen.Overview.route)
//      }
//    }
//    composable(route = DetailsScreen.Overview.route) {
//      ScreenContent(name = DetailsScreen.Overview.route) {
//        navController.popBackStack(
//          route = DetailsScreen.Information.route,
//          inclusive = false
//        )
//      }
//    }
//  }
//}

//sealed class DetailsScreen(val route: String) {
//  object Information : DetailsScreen(route = "INFORMATION")
//  object Overview : DetailsScreen(route = "OVERVIEW")
//}