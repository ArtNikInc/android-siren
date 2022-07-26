package com.adtarassov.siren.data.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
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

  @Headers("Accept: application/json")
  @FormUrlEncoded
  @POST("api/token")
  suspend fun postAuthorizeUser(
    @Field("username")
    username: String,

    @Field("password")
    password: String,
  ): TokenModel

  @Headers("Accept: application/json")
  @FormUrlEncoded
  @POST("api/registration")
  suspend fun postRegisterUser(
    @Field("username")
    username: String,

    @Field("password")
    password: String,
  ): OptionalResponseError?

  companion object {
    const val BASE_URL = "https://reqres.in"
  }
}