package com.adtarassov.siren.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.adtarassov.siren.ui.navigation.MainNavHost
import com.adtarassov.siren.ui.components.BottomBar
import com.adtarassov.siren.ui.components.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
  navController: NavHostController = rememberNavController(),
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