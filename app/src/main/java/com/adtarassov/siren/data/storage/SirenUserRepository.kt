package com.adtarassov.siren.data.storage

import com.adtarassov.siren.data.api.OptionalResponseError
import com.adtarassov.siren.data.api.SirenApi
import com.adtarassov.siren.ui.converters.SirenProfileModelsConverter
import com.adtarassov.siren.ui.models.ProfileUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Field
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SirenUserRepository @Inject constructor(
  private val sirenApi: SirenApi,
  private val userPreferences: UserPreferences,
) {

  fun postAuthorizeUserFlow(username: String, password: String): Flow<Boolean> {
    return flow {
      val tokenModel = sirenApi.postAuthorizeUser(username, password)
      userPreferences.authorizeUser(token = tokenModel.tokenAccess, userName = tokenModel.userName)
      val authSuccess = tokenModel.tokenAccess.isNotBlank() && tokenModel.userName.isNotBlank()
      emit(authSuccess)
    }.flowOn(Dispatchers.IO)
  }

  fun postAuthorizeUserTestFlow(username: String, password: String): Flow<Boolean> {
    return flow {
      delay(1000)
      userPreferences.authorizeUser(token = "test_token", userName = "test_user_name")
      emit(true)
    }.flowOn(Dispatchers.IO)
  }

  fun postRegistrationUserFlow(username: String, password: String): Flow<OptionalResponseError?> {
    return flow {
      val optiErrorModel = sirenApi.postRegisterUser(username, password)
      emit(optiErrorModel)
    }.flowOn(Dispatchers.IO)
  }

  fun postRegistrationUserTestFlow(username: String, password: String): Flow<OptionalResponseError?> {
    return flow {
      delay(2000)
      emit(OptionalResponseError("Error registration"))
    }.flowOn(Dispatchers.IO)
  }

//  fun getProfileUiModelsFlowTest(): Flow<ProfileUiModel> {
//    return flow {
//      delay(100)
//      val profileModel = SirenProfileModelsConverter.createLongTestProfileModel()
//      val profileModelUiModel = profileModelsConverter.convertToProfileUiModel(profileModel)
//      emit(profileModelUiModel)
//    }.flowOn(Dispatchers.IO)
//  }

}