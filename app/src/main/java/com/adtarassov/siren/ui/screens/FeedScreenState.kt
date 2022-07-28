package com.adtarassov.siren.ui.screens

sealed interface FeedScreenState {
  object Loading : FeedScreenState
  object Error : FeedScreenState
  object Success : FeedScreenState
}
