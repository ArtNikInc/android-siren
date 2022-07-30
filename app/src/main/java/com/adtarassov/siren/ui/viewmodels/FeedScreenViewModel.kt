package com.adtarassov.siren.ui.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adtarassov.siren.data.SirenFeedRepository
import com.adtarassov.siren.ui.screens.FeedScreenState
import com.adtarassov.siren.ui.screens.FeedScreenState.Loading
import com.adtarassov.siren.ui.viewmodels.FeedScreenEvent.OnRefresh
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
  private val feedScreenRepository: SirenFeedRepository
) : BaseFlowViewModel<FeedScreenState, FeedScreenEvent>() {

  private val isRefreshingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
  fun isRefreshing(): StateFlow<Boolean> = isRefreshingFlow

  private fun onListRefresh() {
    viewModelScope.launch {
      feedScreenRepository.getSirenFeedUiModelsFlowTest()
        .onStart {
          isRefreshingFlow.emit(true)
        }
        .catch { exception ->
          viewState = FeedScreenState.Error
          isRefreshingFlow.emit(false)
          Log.e(this@FeedScreenViewModel::class.simpleName, exception.stackTrace.toString())
        }
        .collect { feedModels ->
          viewState = FeedScreenState.Success(feedModels)
          isRefreshingFlow.emit(false)
        }
    }
  }

  override fun obtainEvent(viewEvent: FeedScreenEvent) {
    when (viewEvent) {
      OnRefresh -> onListRefresh()
    }
  }

}