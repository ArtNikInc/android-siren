package com.adtarassov.siren.ui.screens.profile

import com.adtarassov.siren.ui.models.SirenFeedUiModel

sealed interface ProfileScreenEvent {
  object OnRefresh : ProfileScreenEvent
  object OnLogout : ProfileScreenEvent
  data class OnScreenEnter(val profileScreenType: ProfileScreenType) : ProfileScreenEvent
  data class OnAuthorizeButtonClick(val userName: String, val userPassword: String) : ProfileScreenEvent
  data class OnRegistrationButtonClick(val userName: String, val userPassword: String) : ProfileScreenEvent

  data class OnItemExpandClick(val model: SirenFeedUiModel) : ProfileScreenEvent
}
