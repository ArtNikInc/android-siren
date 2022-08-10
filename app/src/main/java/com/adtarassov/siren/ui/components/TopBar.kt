package com.adtarassov.siren.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.adtarassov.siren.ui.models.TopBarModel
import com.adtarassov.siren.ui.theme.SirenTheme


@Composable
fun TopBar(
  navController: NavHostController,
  topBarModel: TopBarModel,
) {
  TopAppBar(
    title = {
      Text(
        text = topBarModel.title,
        style = SirenTheme.typography.toolbar
      )
    },
    backgroundColor = SirenTheme.colors.bgMain
  )
}