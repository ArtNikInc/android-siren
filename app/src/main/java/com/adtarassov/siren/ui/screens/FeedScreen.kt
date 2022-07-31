package com.adtarassov.siren.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.adtarassov.siren.ui.components.FeedItemComponent
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.screens.FeedScreenState.Error
import com.adtarassov.siren.ui.screens.FeedScreenState.Loading
import com.adtarassov.siren.ui.screens.FeedScreenState.Success
import com.adtarassov.siren.ui.theme.SirenTheme
import com.adtarassov.siren.ui.viewmodels.FeedScreenEvent
import com.adtarassov.siren.ui.viewmodels.FeedScreenViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
@ExperimentalFoundationApi
fun FeedScreen(
  viewModel: FeedScreenViewModel,
) {
  val viewState = viewModel.viewStates().collectAsState()
  val refreshingState by viewModel.isRefreshing().collectAsState()
  val isRefreshing = rememberSwipeRefreshState(isRefreshing = refreshingState)

  when (val state = viewState.value) {
    is Error -> {

    }
    is Loading -> {

    }
    is Success -> FeedList(
      refreshState = isRefreshing,
      feeds = state.feedList,
      onExpandClick = { viewModel.obtainEvent(FeedScreenEvent.OnItemExpandClick(it)) },
      onRefresh = { viewModel.obtainEvent(FeedScreenEvent.OnRefresh) }
    )
    null -> FeedList(
      refreshState = isRefreshing,
      feeds = emptyList(),
      onExpandClick = { viewModel.obtainEvent(FeedScreenEvent.OnItemExpandClick(it)) },
      onRefresh = { viewModel.obtainEvent(FeedScreenEvent.OnRefresh) }
    )
  }
  LaunchedEffect(key1 = Unit, block = {
    viewModel.obtainEvent(FeedScreenEvent.OnScreenEnter)
  })
}

@ExperimentalFoundationApi
@Composable
fun FeedList(
  refreshState: SwipeRefreshState,
  feeds: List<SirenFeedUiModel>,
  onRefresh: () -> Unit,
  onExpandClick: (SirenFeedUiModel) -> Unit,
) {
  SwipeRefresh(
    modifier = Modifier.background(SirenTheme.colors.bgMinor),
    state = refreshState,
    onRefresh = onRefresh,
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
      verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      items(feeds, key = { it.id }) { feedModel ->
        Row(Modifier.animateItemPlacement()) {
          FeedItemComponent(feedModel) {
            onExpandClick(it)
          }
        }
      }
    }
  }
}