package com.adtarassov.siren.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.adtarassov.siren.ui.screens.feed.FeedScreen
import com.adtarassov.siren.ui.utils.BottomBarScreen
import com.adtarassov.siren.ui.utils.Screens.MAIN_SCREEN
import com.adtarassov.siren.ui.screens.feed.FeedScreenViewModel
import com.adtarassov.siren.ui.screens.profile.ProfileScreen
import com.adtarassov.siren.ui.screens.profile.ProfileScreenType
import com.adtarassov.siren.ui.screens.profile.ProfileScreenType.Type.MAIN
import com.adtarassov.siren.ui.screens.profile.ProfileScreenViewModel
import com.adtarassov.siren.ui.utils.Screens.OTHER_PROFILE_SCREEN

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainNavHost(navController: NavHostController) {
  NavHost(
    navController = navController,
    route = MAIN_SCREEN,
    startDestination = BottomBarScreen.Feed.route
  ) {
    composable(route = BottomBarScreen.Feed.route) { backStackEntry ->
      val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(MAIN_SCREEN)
      }
      val viewModel: FeedScreenViewModel = hiltViewModel(parentEntry)
      FeedScreen(navController = navController, viewModel = viewModel)
    }

    composable(route = BottomBarScreen.Settings.route) {
      Text(text = "title2")
    }

    composable(route = BottomBarScreen.Profile.route) { backStackEntry ->
      val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry(MAIN_SCREEN)
      }
      val viewModel: ProfileScreenViewModel = hiltViewModel(parentEntry)
      ProfileScreen(
        navController = navController,
        viewModel = viewModel,
        profileScreenType = ProfileScreenType(null, MAIN)
      )
    }

    composable(
      route = "$OTHER_PROFILE_SCREEN/{${ProfileScreenType.KEY}}",
      arguments = listOf(
        navArgument(ProfileScreenType.KEY) {
          type = ProfileScreenType.Companion.ProfileScreenNavType
        }
      )
    ) { backStackEntry ->
      val viewModel: ProfileScreenViewModel = hiltViewModel()
      backStackEntry.arguments?.getParcelable<ProfileScreenType>(ProfileScreenType.KEY)?.let {
        ProfileScreen(navController = navController, viewModel = viewModel, profileScreenType = it)
      }
    }
  }
}