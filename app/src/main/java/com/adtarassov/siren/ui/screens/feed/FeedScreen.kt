package com.adtarassov.siren.ui.screens.feed

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.adtarassov.siren.ui.components.FeedList
import com.adtarassov.siren.ui.components.FeedShimmeringList
import com.adtarassov.siren.ui.components.TopBar
import com.adtarassov.siren.ui.screens.feed.FeedScreenEvent.OnItemExpandClick
import com.adtarassov.siren.ui.screens.feed.FeedScreenEvent.OnRefresh
import com.adtarassov.siren.ui.screens.feed.FeedScreenEvent.OnScreenEnter
import com.adtarassov.siren.ui.screens.feed.FeedScreenState.Error
import com.adtarassov.siren.ui.screens.feed.FeedScreenState.Loading
import com.adtarassov.siren.ui.screens.feed.FeedScreenState.Success
import com.adtarassov.siren.ui.theme.SirenTheme
import com.adtarassov.siren.ui.utils.Screens
import com.adtarassov.siren.ui.screens.profile.ProfileScreenType
import com.adtarassov.siren.ui.screens.profile.ProfileScreenType.Type.OTHER
import com.adtarassov.siren.ui.utils.BottomBarScreen
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson

@Composable
@ExperimentalFoundationApi
fun FeedScreen(
  viewModel: FeedScreenViewModel,
  navController: NavHostController,
) {
  val viewState = viewModel.viewStates().collectAsState()
  val refreshingState by viewModel.isRefreshing().collectAsState()
  val topBarModel by viewModel.topBarFlow().collectAsState()
  val isRefreshing = rememberSwipeRefreshState(isRefreshing = refreshingState)

  Scaffold(
    topBar = {
      TopBar(navController = navController, topBarModel = topBarModel)
    },
    backgroundColor = SirenTheme.colors.bgMinor
  ) { innerPadding ->
    Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
      when (val state = viewState.value) {
        is Error -> {

        }
        is Loading -> {
          FeedShimmeringList()
        }
        is Success -> FeedList(
          refreshState = isRefreshing,
          feeds = state.feedList,
          onExpandClick = { viewModel.obtainEvent(OnItemExpandClick(it)) },
          onRefresh = { viewModel.obtainEvent(OnRefresh) },
          onAuthorClick = {
            val profileScreenTypeJsonModel = Uri.encode(Gson().toJson(ProfileScreenType(it.authorName, OTHER)))
            navController.navigate("${BottomBarScreen.Feed.route}/${Screens.OTHER_PROFILE_SCREEN}/$profileScreenTypeJsonModel") {
            }
          }
        )
        null -> {
          FeedShimmeringList()
        }
      }
    }
  }
  LaunchedEffect(key1 = Unit, block = {
    viewModel.obtainEvent(OnScreenEnter)
  })
}