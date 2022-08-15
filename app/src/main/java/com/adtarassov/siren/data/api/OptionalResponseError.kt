package com.adtarassov.siren.data.api

import com.google.gson.annotations.SerializedName

data class OptionalResponseError(
  @SerializedName("error")
  val errorText: String?
) {
}