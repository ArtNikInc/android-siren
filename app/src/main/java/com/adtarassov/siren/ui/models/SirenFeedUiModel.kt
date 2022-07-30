package com.adtarassov.siren.ui.models

import android.graphics.Bitmap

data class SirenFeedUiModel(
  val header: String,
  val body: String,
  val authorImage: Bitmap?
)