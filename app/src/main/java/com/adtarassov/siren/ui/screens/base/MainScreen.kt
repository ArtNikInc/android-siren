package com.adtarassov.siren.ui.screens.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.adtarassov.siren.ui.navigation.MainNavHost
import com.adtarassov.siren.ui.components.BottomBar

@Composable
fun MainScreen(
  navController: NavHostController,
) {
  Scaffold(
    bottomBar = {
      BottomBar(navController = navController)
    }
  ) { innerPadding ->
    Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
      MainNavHost(navController = navController)
    }
  }
}