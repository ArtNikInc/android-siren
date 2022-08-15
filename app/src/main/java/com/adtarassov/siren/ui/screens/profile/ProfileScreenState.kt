package com.adtarassov.siren.ui.screens.profile

import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.models.ProfileUiModel

sealed interface ProfileScreenState {

  data class NotAuthorize(
    val errorText: String,
    val isAuthProgress: Boolean,
    val isRegProgress: Boolean
  ) : ProfileScreenState

  object LoadingError : ProfileScreenState

  data class Success(
    val profileModel: ProfileUiModel,
    val feedList: List<SirenFeedUiModel>,
  ) : ProfileScreenState
}
