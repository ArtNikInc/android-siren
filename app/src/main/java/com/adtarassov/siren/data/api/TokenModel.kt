package com.adtarassov.siren.data.api

import com.google.gson.annotations.SerializedName

data class TokenModel(
  @SerializedName("access")
  val tokenAccess: String,
  @SerializedName("userName")
  val userName: String,
)