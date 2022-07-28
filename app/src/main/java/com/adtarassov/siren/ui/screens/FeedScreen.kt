package com.adtarassov.siren.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adtarassov.siren.ui.components.FeedItemComponent
import com.adtarassov.siren.ui.screens.FeedScreenState.Error
import com.adtarassov.siren.ui.screens.FeedScreenState.Loading
import com.adtarassov.siren.ui.screens.FeedScreenState.Success
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScreen(
  viewModel: FeedScreenViewModel = hiltViewModel(),
) {
  val viewState = viewModel.viewStates().collectAsState()
  when (val state = viewState.value) {
    Error -> {

    }
    Loading -> {

    }
    Success -> FeedList(listOf("", ""))

    null -> FeedList(listOf("", ""))
  }
  LaunchedEffect(key1 = viewState, block = {
  })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedList(
  feeds: List<String>
) {
  LazyColumn(
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
    items(feeds) { feedModel ->
      FeedItemComponent(feedModel)
    }
  }
}