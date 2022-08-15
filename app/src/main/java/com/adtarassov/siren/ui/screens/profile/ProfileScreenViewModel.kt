package com.adtarassov.siren.ui.screens.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.adtarassov.siren.R
import com.adtarassov.siren.data.storage.SirenFeedRepository
import com.adtarassov.siren.data.storage.SirenProfileRepository
import com.adtarassov.siren.data.storage.SirenUserRepository
import com.adtarassov.siren.data.storage.UserPreferences
import com.adtarassov.siren.ui.BaseFlowViewModel
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.models.TopBarUiModel
import com.adtarassov.siren.ui.models.ProfileUiModel
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnAuthorizeButtonClick
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnItemExpandClick
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnLogout
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnRefresh
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnRegistrationButtonClick
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnScreenEnter
import com.adtarassov.siren.ui.screens.profile.ProfileScreenState.Success
import com.adtarassov.siren.ui.screens.profile.ProfileScreenType.Main
import com.adtarassov.siren.ui.screens.profile.ProfileScreenType.Other
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
  private val profileRepository: SirenProfileRepository,
  private val feedRepository: SirenFeedRepository,
  private val userPreferences: UserPreferences,
  private val userRepository: SirenUserRepository,
  @ApplicationContext
  context: Context,
) : BaseFlowViewModel<ProfileScreenState, ProfileScreenEvent>() {

  private val registrationErrorString = context.getString(R.string.registration_response_error)
  private val authErrorString = context.getString(R.string.auth_response_error)

  private lateinit var profileScreenType: ProfileScreenType // must be create in OnScreenEnter event

  private val isRefreshingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
  fun isRefreshing(): StateFlow<Boolean> = isRefreshingFlow

  private val topBarFlow: MutableStateFlow<TopBarUiModel> = MutableStateFlow(
    TopBarUiModel(
      title = context.getString(R.string.tool_bar_profile_screen_title),
      hasElevation = false
    )
  )

  fun topBarFlow(): StateFlow<TopBarUiModel> = topBarFlow

  private fun profileInformationRefresh(profileName: String, token: String?) {
    viewModelScope.launch {
      combine(
        profileRepository.getProfileUiModelsFlowTest(profileName, token),
        feedRepository.getSirenProfileFeedUiModelsTestFlow(profileName, token)
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
        .collectLatest { pair ->
          val profileUiModel = pair.first
          val feedList = pair.second
          viewState = Success(profileUiModel, feedList)
          isRefreshingFlow.emit(false)
        }
    }
  }

  private fun doOnOtherProfileScreen(profileName: String) {
    val userAuthModel = userPreferences.userAuthModelFlow().value
    profileInformationRefresh(profileName, userAuthModel.token)
  }

  private fun doOnMainProfileScreen() {
    val userAuthModel = userPreferences.userAuthModelFlow().value
    if (!userAuthModel.isAuth()) {
      viewState = ProfileScreenState.NotAuthorize("", isAuthProgress = false, isRegProgress = false)
      return
    }
    if (viewStates().value is Success) {
      return
    }

    userAuthModel.userName?.let { profileInformationRefresh(it, userAuthModel.token) }
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
      is OnRefresh -> onUserRefresh()
      is OnLogout -> onLogoutButtonClick()
      is OnScreenEnter -> {
        val type = viewEvent.profileScreenType
        profileScreenType = type
        when (type) {
          is Main -> {
            doOnMainProfileScreen()
          }
          is Other -> {
            doOnOtherProfileScreen(type.profileName)
          }
        }
      }
      is OnItemExpandClick -> onItemExpandClick(viewEvent.model)
      is OnAuthorizeButtonClick -> onAuthorizeButtonClick(viewEvent.userName, viewEvent.userPassword)
      is OnRegistrationButtonClick -> onRegistrationButtonClick(viewEvent.userName, viewEvent.userPassword)
    }
  }

  private fun onUserRefresh() {
    when (profileScreenType) {
      is Main -> {
        doOnMainProfileScreen()
      }
      is Other -> {
        doOnOtherProfileScreen((profileScreenType as Other).profileName)
      }
    }
  }

  private fun onRegistrationButtonClick(userName: String, userPassword: String) {
    viewModelScope.launch {
      userRepository.postRegistrationUserTestFlow(userName, userPassword)
        .onStart {
          viewState =
            ProfileScreenState.NotAuthorize("", isAuthProgress = false, isRegProgress = true)
        }
        .catch {
          viewState =
            ProfileScreenState.NotAuthorize(registrationErrorString, isAuthProgress = false, isRegProgress = false)
        }.collectLatest {
          viewState =
            ProfileScreenState.NotAuthorize(it?.errorText ?: "", isAuthProgress = false, isRegProgress = false)
        }
    }
  }

  private fun onAuthorizeButtonClick(userName: String, userPassword: String) {
    viewModelScope.launch {
      userRepository.postAuthorizeUserTestFlow(userName, userPassword)
        .onStart {
          viewState =
            ProfileScreenState.NotAuthorize("", isAuthProgress = true, isRegProgress = false)
        }
        .catch {
          viewState =
            ProfileScreenState.NotAuthorize(authErrorString, isAuthProgress = false, isRegProgress = false)
        }.collectLatest { isSuccess ->
          if (isSuccess) {
            doOnMainProfileScreen()
          } else {
            viewState =
              ProfileScreenState.NotAuthorize(authErrorString, isAuthProgress = false, isRegProgress = false)
          }
        }
    }
  }

  private fun onLogoutButtonClick() {
    userPreferences.logoutUser()
    viewState = ProfileScreenState.NotAuthorize(
      "",
      isAuthProgress = false,
      isRegProgress = false
    )
  }

}