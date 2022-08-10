package com.adtarassov.siren.data

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

  fun getSirenFeedUiModelsFlow(): Flow<List<SirenFeedUiModel>> {
    return flow {
      val fooList = sirenApi.getFeed().map { model ->
        sirenFeedModelsConverter.convertToFeedModel(model)
      }
      emit(fooList)
    }.flowOn(Dispatchers.IO)
  }

  fun getSirenFeedUiModelsFlowTest(): Flow<List<SirenFeedUiModel>> {
    return flow {
      delay(100)
      val list = listOf(
        SirenFeedModelsConverter.createTestFeedModel(),
        SirenFeedModelsConverter.createTestFeedModel(),
        SirenFeedModelsConverter.createLongTestFeedModel(),
        SirenFeedModelsConverter.createTestFeedModel(),
      ).map { model ->
        sirenFeedModelsConverter.convertToFeedModel(model)
      }
      emit(list)
    }.flowOn(Dispatchers.IO)
  }

}