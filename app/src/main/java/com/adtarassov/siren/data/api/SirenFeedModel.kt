package com.adtarassov.siren.data.api

import com.google.gson.annotations.SerializedName

data class SirenFeedModel(
  @SerializedName("id")
  val id: Long,
  @SerializedName("header")
  val header: String,
  @SerializedName("body")
  val body: String,
  @SerializedName("author_id")
  val authorId: Long,
  @SerializedName("author_image_url")
  val authorImageUrl: String?
)