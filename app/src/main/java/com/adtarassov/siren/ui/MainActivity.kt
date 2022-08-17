package com.adtarassov.siren.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.rememberNavController
import com.adtarassov.siren.ui.navigation.SirenNavHost
import com.adtarassov.siren.ui.screens.profile.ProfileScreenViewModel
import com.adtarassov.siren.ui.theme.SirenTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @EntryPoint
  @InstallIn(ActivityComponent::class)
  interface ViewModelFactoryProvider {
    fun profileViewModelFactory(): ProfileScreenViewModel.Factory
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SirenTheme {
        val navController = rememberAnimatedNavController()
        SirenNavHost(navController = navController)
      }
    }
  }
}