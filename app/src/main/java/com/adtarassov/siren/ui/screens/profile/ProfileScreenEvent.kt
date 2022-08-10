package com.adtarassov.siren.ui.screens.profile

import com.adtarassov.siren.ui.models.SirenFeedUiModel

sealed interface ProfileScreenEvent {
  object OnRefresh : ProfileScreenEvent
  object OnScreenEnter : ProfileScreenEvent
  data class OnItemExpandClick(val model: SirenFeedUiModel) : ProfileScreenEvent
}
