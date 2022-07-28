package com.adtarassov.siren.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
  val route: String,
  val title: String,
  val icon: ImageVector
) {
  object Feed : BottomBarScreen(
    route = "FEED",
    title = "Feed",
    icon = Icons.Default.List
  )

  object Profile : BottomBarScreen(
    route = "SIREN",
    title = "Siren",
    icon = Icons.Default.Share
  )

  object Settings : BottomBarScreen(
    route = "PROFILE",
    title = "Profile",
    icon = Icons.Default.Person
  )
}