package com.adtarassov.siren.ui.models

import android.graphics.Bitmap
import androidx.compose.runtime.Immutable

@Immutable
data class SirenFeedUiModel(
  val id: Long,
  val header: String,
  val body: String,
  val authorImage: Bitmap?,
  val isExpanded: Boolean = false
  )