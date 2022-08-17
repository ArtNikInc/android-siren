package com.adtarassov.siren.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.theme.SirenTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalFoundationApi
@Composable
fun FeedList(
  refreshState: SwipeRefreshState,
  feeds: List<SirenFeedUiModel>,
  onRefresh: () -> Unit,
  onExpandClick: (SirenFeedUiModel) -> Unit,
  onAuthorClick: (SirenFeedUiModel) -> Unit,
  isShimmering: Boolean = false,
  header: (@Composable () -> Unit)? = null,
) {
  val hasHeader = header != null
  SwipeRefresh(
    state = refreshState,
    onRefresh = onRefresh,
    swipeEnabled = !isShimmering
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(bottom = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      if (isShimmering) {
        item {
          Box(Modifier.padding(top = 16.dp)) {
            ShimmerFeedPlaceHolderAnimation()
          }
        }
        items(4) {
          ShimmerFeedPlaceHolderAnimation()
        }
      } else {
        header?.let {
          item {
            header.invoke()
          }
        }
        itemsIndexed(feeds, key = { _, feedModel -> feedModel.id }) { i, feedModel ->
          val isFirst = i == 0
          Row(
            Modifier
              .animateItemPlacement()
              .padding(start = 16.dp, end = 16.dp, top = if (!hasHeader && isFirst) 16.dp else 0.dp)
          ) {
            FeedItemComponent(feedModel, onAuthorClick)
//          FeedAnimatedItemComponent(feedModel) {
//            onExpandClick(it)
//          }
          }
        }
      }
    }
  }
}

@ExperimentalFoundationApi
@Composable
@Preview
fun FeedShimmeringListPreview(
) {
  FeedShimmeringList()
}

@ExperimentalFoundationApi
@Composable
fun FeedShimmeringList(
) {
  FeedList(
    rememberSwipeRefreshState(isRefreshing = false),
    emptyList(),
    { },
    { },
    { },
    isShimmering = true
  )
}