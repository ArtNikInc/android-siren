package com.adtarassov.siren.ui.models

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Immutable

@Immutable
data class SirenFeedUiModel(
  val id: Long,
  val header: String,
  val body: String,
  val authorImage: Drawable?,
  val authorName: String,
  val isExpanded: Boolean = false
  )