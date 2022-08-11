package com.adtarassov.siren.ui.models

import android.graphics.drawable.Drawable

data class ProfileUiModel(
  val id: Long,
  val name: String,
  val description: String,
  val profileIcon: Drawable?,
  val statistic: Statistic,
) {
  data class Statistic(
    val views: Long,
    val likes: Long,
    val followers: Long,
  )
}