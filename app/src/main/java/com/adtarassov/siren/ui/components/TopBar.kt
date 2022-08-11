package com.adtarassov.siren.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    title = {
      Text(
        text = topBarModel.title,
        style = SirenTheme.typography.toolbar
      )
    },
    elevation = if (topBarModel.hasElevation) AppBarDefaults.TopAppBarElevation else 0.dp,
    backgroundColor = SirenTheme.colors.bgMain
  )
}