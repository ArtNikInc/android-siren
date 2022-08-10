package com.adtarassov.siren.ui.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adtarassov.siren.R
import com.adtarassov.siren.data.storage.SirenFeedRepository
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.models.TopBarModel
import com.adtarassov.siren.ui.screens.FeedScreenEvent.OnItemExpandClick
import com.adtarassov.siren.ui.screens.FeedScreenEvent.OnRefresh
import com.adtarassov.siren.ui.screens.FeedScreenEvent.OnScreenEnter
import com.adtarassov.siren.ui.BaseFlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
  private val feedScreenRepository: SirenFeedRepository,
  @ApplicationContext
  context: Context,
) : BaseFlowViewModel<FeedScreenState, FeedScreenEvent>() {

  private val isRefreshingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
  fun isRefreshing(): StateFlow<Boolean> = isRefreshingFlow

  private val topBarFlow: MutableStateFlow<TopBarModel> = MutableStateFlow(
    TopBarModel(
      title = context.getString(R.string.tool_bar_feed_screen_title)
    )
  )

  fun topBarFlow(): StateFlow<TopBarModel> = topBarFlow

  private fun onScreenEnter() {
    if (viewStates().value is FeedScreenState.Success) {
      return
    }
    onListRefresh()
  }

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

  private fun onItemExpandClick(model: SirenFeedUiModel) {
    val currentState = viewStates().value
    if (currentState !is FeedScreenState.Success) {
      return
    }
    val newList = currentState.feedList.map {
      if (it == model) {
        return@map model.copy(isExpanded = !model.isExpanded)
      }
      it
    }
    viewState = FeedScreenState.Success(newList)
  }

  override fun obtainEvent(viewEvent: FeedScreenEvent) {
    when (viewEvent) {
      OnRefresh -> onListRefresh()
      OnScreenEnter -> onScreenEnter()
      is OnItemExpandClick -> onItemExpandClick(viewEvent.model)
    }
  }

}