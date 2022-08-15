package com.adtarassov.siren.data.storage

import com.adtarassov.siren.data.api.SirenApi
import com.adtarassov.siren.ui.converters.SirenFeedModelsConverter
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SirenFeedRepository @Inject constructor(
  private val sirenApi: SirenApi,
  private val sirenFeedModelsConverter: SirenFeedModelsConverter,
) {

  fun getSirenRecommendationFeedUiModelsFlow(): Flow<List<SirenFeedUiModel>> {
    return flow {
      val filteredList = sirenApi.getRecommendationFeed().map { model ->
        sirenFeedModelsConverter.convertToFeedModel(model)
      }
      emit(filteredList)
    }.flowOn(Dispatchers.IO)
  }

  fun getSirenProfileFeedUiModelsFlow(profileName: String, token: String?): Flow<List<SirenFeedUiModel>> {
    return flow {
      val responseList = if (token.isNullOrBlank()) {
        sirenApi.getProfileFeed(profileName = profileName)
      } else {
        sirenApi.getProfileFeed(token = token, profileName = profileName)
      }
      val filteredList = responseList.map { model ->
        sirenFeedModelsConverter.convertToFeedModel(model)
      }
      emit(filteredList)
    }.flowOn(Dispatchers.IO)
  }

  fun getSirenProfileFeedUiModelsTestFlow(profileName: String, token: String?): Flow<List<SirenFeedUiModel>> {
    return getSirenFeedUiModelsFlowTest()
  }

  fun getSirenFeedUiModelsFlowTest(): Flow<List<SirenFeedUiModel>> {
    return flow {
      delay(100)
      val list = listOf(
        SirenFeedModelsConverter.createTestFeedModel(),
        SirenFeedModelsConverter.createTestFeedModel(),
        SirenFeedModelsConverter.createLongTestFeedModel(),
        SirenFeedModelsConverter.createTestFeedModel(),
        SirenFeedModelsConverter.createLongTestFeedModel(),
        SirenFeedModelsConverter.createLongTestFeedModel(),
        SirenFeedModelsConverter.createLongTestFeedModel(),
      ).map { model ->
        sirenFeedModelsConverter.convertToFeedModel(model)
      }
      emit(list)
    }.flowOn(Dispatchers.IO)
  }

}