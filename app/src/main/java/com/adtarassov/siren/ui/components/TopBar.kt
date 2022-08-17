package com.adtarassov.siren.ui.components

import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adtarassov.siren.ui.models.TopBarUiModel
import com.adtarassov.siren.ui.theme.SirenTheme


@Composable
fun TopBar(
  navController: NavHostController,
  topBarModel: TopBarUiModel,
) {
  TopAppBar(
    navigationIcon = if (canShowArrowBackButton(navController, topBarModel)) {
      {
        IconButton(onClick = { navController.navigateUp() }) {
          Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back"
          )
        }
      }
    } else {
      null
    },
    title = {
      Text(
        text = topBarModel.title,
        style = SirenTheme.typography.toolbar,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    elevation = if (topBarModel.hasElevation) AppBarDefaults.TopAppBarElevation else 0.dp,
    backgroundColor = SirenTheme.colors.bgMain
  )
}

private fun canShowArrowBackButton(
  navController: NavHostController,
  topBarModel: TopBarUiModel
): Boolean {
  val navBackStackEntry = navController.currentBackStackEntry
  val currentDestination = navBackStackEntry?.destination
  val bottomBarDestination = bottomBarScreens.any { it.route == currentDestination?.route }

  return navController.previousBackStackEntry != null && !bottomBarDestination
}