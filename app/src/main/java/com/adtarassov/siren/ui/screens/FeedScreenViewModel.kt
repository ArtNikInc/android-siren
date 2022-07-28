package com.adtarassov.siren.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adtarassov.siren.ui.viewmodel.BaseFlowViewModel
import com.adtarassov.siren.ui.viewmodel.FeedScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(

) : BaseFlowViewModel<FeedScreenState, FeedScreenEvent>() {

  override fun obtainEvent(viewEvent: FeedScreenEvent) {
  }

}