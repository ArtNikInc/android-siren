package com.adtarassov.siren.ui.screens.feed

import com.adtarassov.siren.ui.models.SirenFeedUiModel

sealed interface FeedScreenState {
  object Loading : FeedScreenState
  object Error : FeedScreenState
  data class Success(val feedList: List<SirenFeedUiModel>) : FeedScreenState
}
