package com.adtarassov.siren.data.storage

import com.adtarassov.siren.data.api.SirenApi
import com.adtarassov.siren.ui.converters.SirenFeedModelsConverter
import com.adtarassov.siren.ui.converters.SirenProfileModelsConverter
import com.adtarassov.siren.ui.models.ProfileUiModel
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SirenProfileRepository @Inject constructor(
  private val sirenApi: SirenApi,
  private val profileModelsConverter: SirenProfileModelsConverter,
) {

  fun getProfileUiModelsFlow(profileName: String, token: String?): Flow<ProfileUiModel> {
    return flow {
      val profileModel = if (token.isNullOrBlank()) {
        sirenApi.getProfile(profileName = profileName)
      } else {
        sirenApi.getProfile(token = token, profileName = profileName)
      }
      val profileModelUiModel = profileModelsConverter.convertToProfileUiModel(profileModel)
      emit(profileModelUiModel)
    }.flowOn(Dispatchers.IO)
  }

  fun getProfileUiModelsFlowTest(profileName: String, token: String?): Flow<ProfileUiModel> {
    return flow {
      delay(100)
      val profileModel = SirenProfileModelsConverter.createLongTestProfileModel().copy(
        name = profileName
      )
      val profileModelUiModel = profileModelsConverter.convertToProfileUiModel(profileModel)
      emit(profileModelUiModel)
    }.flowOn(Dispatchers.IO)
  }

}