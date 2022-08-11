package com.adtarassov.siren.ui.screens.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adtarassov.siren.R
import com.adtarassov.siren.data.storage.SirenFeedRepository
import com.adtarassov.siren.data.storage.SirenProfileRepository
import com.adtarassov.siren.ui.BaseFlowViewModel
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.models.TopBarUiModel
import com.adtarassov.siren.ui.models.ProfileUiModel
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnItemExpandClick
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnRefresh
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnScreenEnter
import com.adtarassov.siren.ui.screens.profile.ProfileScreenState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
  private val profileRepository: SirenProfileRepository,
  private val feedRepository: SirenFeedRepository,
  @ApplicationContext
  context: Context,
) : BaseFlowViewModel<ProfileScreenState, ProfileScreenEvent>() {

  private val isRefreshingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
  fun isRefreshing(): StateFlow<Boolean> = isRefreshingFlow

  private val topBarFlow: MutableStateFlow<TopBarUiModel> = MutableStateFlow(
    TopBarUiModel(
      title = context.getString(R.string.tool_bar_profile_screen_title),
      hasElevation = false
    )
  )

  fun topBarFlow(): StateFlow<TopBarUiModel> = topBarFlow

  private fun onScreenEnter() {
    if (viewStates().value is Success) {
      return
    }
    onListRefresh()
  }

  private fun onListRefresh() {
    viewModelScope.launch {
      combine(
        profileRepository.getProfileUiModelsFlowTest(),
        feedRepository.getSirenFeedUiModelsFlowTest()
      ) { profileUiModel: ProfileUiModel, feedList: List<SirenFeedUiModel> ->
        profileUiModel to feedList
      }
        .onStart {
          isRefreshingFlow.emit(true)
        }
        .catch { exception ->
          viewState = ProfileScreenState.LoadingError
          isRefreshingFlow.emit(false)
          Log.e(this@ProfileScreenViewModel::class.simpleName, exception.stackTrace.toString())
        }
        .collect { pair ->
          val profileUiModel = pair.first
          val feedList = pair.second
          viewState = Success(profileUiModel, feedList)
          isRefreshingFlow.emit(false)
        }
    }
  }

  private fun onItemExpandClick(model: SirenFeedUiModel) {
    val currentState = viewStates().value
    if (currentState !is Success) {
      return
    }
    val newList = currentState.feedList.map {
      if (it == model) {
        return@map model.copy(isExpanded = !model.isExpanded)
      }
      it
    }
    viewState = Success(currentState.profileModel, newList)
  }

  override fun obtainEvent(viewEvent: ProfileScreenEvent) {
    when (viewEvent) {
      OnRefresh -> onListRefresh()
      OnScreenEnter -> onScreenEnter()
      is OnItemExpandClick -> onItemExpandClick(viewEvent.model)
    }
  }

}