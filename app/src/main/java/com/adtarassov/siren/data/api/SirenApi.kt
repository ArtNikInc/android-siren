package com.adtarassov.siren.data.api

import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface SirenApi {

  @GET("api/recommendation")
  suspend fun getRecommendationFeed(): List<SirenFeedModel>

  @GET("api/profile/{profile_name}/posts")
  suspend fun getProfileFeed(
    @Header("Authorization")
    token: String,
    @Path("profile_name")
    profileName: String,
  ): List<SirenFeedModel>

  @GET("api/profile/{profile_name}/posts")
  suspend fun getProfileFeed(
    @Path("profile_name")
    profileName: String,
  ): List<SirenFeedModel>


  @GET("api/profile/{profile_name}")
  suspend fun getProfile(
    @Path("profile_name")
    profileName: String,
  ): ProfileModel

  @GET("api/profile/{profile_name}")
  suspend fun getProfile(
    @Header("Authorization")
    token: String,
    @Path("profile_name")
    profileName: String,
  ): ProfileModel

  companion object {
    const val BASE_URL = "https://reqres.in"
  }
}