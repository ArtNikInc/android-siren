package com.adtarassov.siren.data.api

import retrofit2.Response
import retrofit2.http.GET

interface SirenApi {

  @GET("api/audio/recommendation")
  suspend fun getFeed(): List<SirenFeedModel>

  companion object {
    const val BASE_URL = "https://reqres.in"
  }
}