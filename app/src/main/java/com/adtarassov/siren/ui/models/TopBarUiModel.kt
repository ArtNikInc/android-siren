package com.adtarassov.siren.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class TopBarUiModel(
  val title: String,
  val hasElevation: Boolean
)
