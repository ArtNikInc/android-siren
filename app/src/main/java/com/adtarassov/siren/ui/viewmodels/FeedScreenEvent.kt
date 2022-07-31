package com.adtarassov.siren.ui.viewmodels

import com.adtarassov.siren.ui.models.SirenFeedUiModel

sealed interface FeedScreenEvent {
  object OnRefresh : FeedScreenEvent
  object OnScreenEnter : FeedScreenEvent
  data class OnItemExpandClick(val model: SirenFeedUiModel) : FeedScreenEvent
}
