package com.adtarassov.siren.ui.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector
import com.adtarassov.siren.R
import com.adtarassov.siren.ui.utils.Screens.FEED_SCREEN
import com.adtarassov.siren.ui.utils.Screens.PROFILE_SCREEN
import com.adtarassov.siren.ui.utils.Screens.SIREN_SCREEN

sealed class BottomBarScreen(
  val route: String,
  @StringRes
  val titleId: Int,
  val icon: ImageVector
) {
  object Feed : BottomBarScreen(
    route = FEED_SCREEN,
    titleId = R.string.tool_bar_feed_screen_title,
    icon = Icons.Default.List
  )

  object Profile : BottomBarScreen(
    route = SIREN_SCREEN,
    titleId = R.string.bottom_bar_siren_screen_title,
    icon = Icons.Default.Share
  )

  object Settings : BottomBarScreen(
    route = PROFILE_SCREEN,
    titleId = R.string.bottom_bar_profile_screen_title,
    icon = Icons.Default.Person
  )
}