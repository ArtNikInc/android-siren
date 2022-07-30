package com.adtarassov.siren

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.adtarassov.siren.ui.navigation.SirenNavHost
import com.adtarassov.siren.ui.theme.SirenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      SirenTheme {
        val navController = rememberNavController()
        SirenNavHost(navController = navController)
      }
    }
  }
}