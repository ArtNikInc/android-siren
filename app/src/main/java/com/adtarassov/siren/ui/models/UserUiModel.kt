package com.adtarassov.siren.ui.models

import android.graphics.drawable.Drawable

data class UserUiModel(
  val userName: String,
  val userDescription: String,
  val userImage: Drawable?,
)