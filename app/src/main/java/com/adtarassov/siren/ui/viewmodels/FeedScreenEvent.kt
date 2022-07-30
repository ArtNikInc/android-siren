package com.adtarassov.siren.ui.viewmodels

sealed interface FeedScreenEvent {
  object OnRefresh : FeedScreenEvent
}
