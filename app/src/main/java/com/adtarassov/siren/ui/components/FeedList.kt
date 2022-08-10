package com.adtarassov.siren.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.theme.SirenTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState


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
      verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
      items(feeds, key = { it.id }) { feedModel ->
        Row(Modifier.animateItemPlacement()) {
          FeedItemComponent(feedModel)
//          FeedAnimatedItemComponent(feedModel) {
//            onExpandClick(it)
//          }
        }
      }
    }
  }
}