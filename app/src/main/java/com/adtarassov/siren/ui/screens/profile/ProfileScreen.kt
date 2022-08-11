package com.adtarassov.siren.ui.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adtarassov.siren.ui.components.FeedList
import com.adtarassov.siren.ui.components.TopBar
import com.adtarassov.siren.ui.models.ProfileUiModel
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnItemExpandClick
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnRefresh
import com.adtarassov.siren.ui.screens.profile.ProfileScreenEvent.OnScreenEnter
import com.adtarassov.siren.ui.screens.profile.ProfileScreenState.LoadingError
import com.adtarassov.siren.ui.screens.profile.ProfileScreenState.NotAuthorize
import com.adtarassov.siren.ui.screens.profile.ProfileScreenState.Success
import com.adtarassov.siren.ui.theme.SirenTheme
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
  navController: NavHostController,
  viewModel: ProfileScreenViewModel,
) {
  val viewState = viewModel.viewStates().collectAsState()
  val refreshingState by viewModel.isRefreshing().collectAsState()
  val topBarModel by viewModel.topBarFlow().collectAsState()
  val isRefreshing = rememberSwipeRefreshState(isRefreshing = refreshingState)

  Scaffold(
    topBar = {
      Column {
        TopBar(navController = navController, topBarModel = topBarModel)
        if (!topBarModel.hasElevation) {
          Spacer(
            modifier = Modifier
              .height(1.dp)
              .background(SirenTheme.colors.bgMinor)
          )
        }
      }
    },
    backgroundColor = SirenTheme.colors.bgMinor
  ) { innerPadding ->
    Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
      when (val state = viewState.value) {
        LoadingError -> {

        }
        NotAuthorize -> {

        }
        is Success -> {
          Column {
            FeedList(
              refreshState = isRefreshing,
              feeds = state.feedList,
              onExpandClick = { viewModel.obtainEvent(OnItemExpandClick(it)) },
              onRefresh = { viewModel.obtainEvent(OnRefresh) }
            ) {
              ProfileHeader(state.profileModel)
            }
          }
        }
        null -> {
          FeedList(
            refreshState = isRefreshing,
            feeds = emptyList(),
            onExpandClick = { viewModel.obtainEvent(OnItemExpandClick(it)) },
            onRefresh = { viewModel.obtainEvent(OnRefresh) }
          )
        }
      }
    }
  }
  LaunchedEffect(key1 = Unit, block = {
    viewModel.obtainEvent(OnScreenEnter)
  })
}

@Composable
fun ProfileHeader(
  model: ProfileUiModel,
) {
  Card(
    backgroundColor = SirenTheme.colors.bgMain,
    elevation = 8.dp,
    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
    modifier = Modifier
      .fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        model.profileIcon?.let { drawable ->
          Image(
            painter = rememberDrawablePainter(drawable = drawable),
            contentDescription = model.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .padding(end = 4.dp)
              .size(64.dp)
              .clip(CircleShape)
              .background(SirenTheme.colors.bgMinor)
          )
        }
        Text(
          text = model.name,
          style = SirenTheme.typography.heading,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      Spacer(modifier = Modifier.height(2.dp))

      Text(
        text = model.description,
        style = SirenTheme.typography.body,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
      )

    }
  }
}