package com.adtarassov.siren.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseFlowViewModel<S, E>: ViewModel() {

  private val _viewStates: MutableStateFlow<S?> = MutableStateFlow(null)
  fun viewStates(): StateFlow<S?> = _viewStates

  protected var viewState: S
    get() = _viewStates.value
      ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
    set(value) {
      if (_viewStates.value == value) {
        _viewStates.value = null
      }
      _viewStates.value = value
    }

  abstract fun obtainEvent(viewEvent: E)
}