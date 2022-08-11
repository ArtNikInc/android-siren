package com.adtarassov.siren.data.api

import com.google.gson.annotations.SerializedName

data class ProfileModel(
  @SerializedName("id")
  val id: Long,
  @SerializedName("name")
  val name: String,
  @SerializedName("image_url")
  val imageUrl: String?,
  @SerializedName("description")
  val description: String,
  @SerializedName("statistic")
  val statistic: ProfileStatistic
)

data class ProfileStatistic(
  @SerializedName("likes")
  val likes: Long,
  @SerializedName("views")
  val views: Long,
  @SerializedName("followers")
  val followers: Long
)