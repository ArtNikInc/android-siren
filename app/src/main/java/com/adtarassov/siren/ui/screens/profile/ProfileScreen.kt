package com.adtarassov.siren.ui.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adtarassov.siren.R.string
import com.adtarassov.siren.ui.components.FeedList
import com.adtarassov.siren.ui.components.SirenLoadingButton
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
  profileScreenType: ProfileScreenType,
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
    Box(
      modifier = Modifier
        .padding(bottom = innerPadding.calculateBottomPadding())
    ) {
      when (val state = viewState.value) {
        LoadingError -> {

        }
        is NotAuthorize -> {
          LoginScreen(state, viewModel)
        }
        is Success -> {
          FeedList(
            refreshState = isRefreshing,
            feeds = state.feedList,
            onExpandClick = { viewModel.obtainEvent(OnItemExpandClick(it)) },
            onRefresh = { viewModel.obtainEvent(OnRefresh) },
            onAuthorClick = { /* no-op */ }
          ) {
            ProfileHeader(state.profileModel, viewModel)
          }

        }
        null -> {
          Column(Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
              color = SirenTheme.colors.mainText,
              strokeWidth = 2.dp
            )
          }
        }
      }
    }
  }
  LaunchedEffect(key1 = Unit, block = {
    viewModel.obtainEvent(OnScreenEnter(profileScreenType))
  })
}

@Composable
fun ProfileHeader(
  model: ProfileUiModel,
  viewModel: ProfileScreenViewModel,
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
              .clickable {
                viewModel.obtainEvent(ProfileScreenEvent.OnLogout)
              }
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

@Composable
fun LoginScreen(
  model: NotAuthorize,
  viewModel: ProfileScreenViewModel,
) {
  val isLoading = model.isAuthProgress || model.isRegProgress
  Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Card(
      modifier = Modifier.padding(horizontal = 16.dp),
      elevation = 8.dp,
      shape = SirenTheme.shapes.medium
    ) {
      Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        var userName by remember { mutableStateOf("") }
        OutlinedTextField(
          value = userName,
          enabled = !isLoading,
          onValueChange = { userName = it },
          label = { Text(stringResource(string.auth_user_name)) },
          singleLine = true,
          modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
        )
        var userPassword by remember { mutableStateOf("") }
        OutlinedTextField(
          value = userPassword,
          enabled = !isLoading,
          onValueChange = { userPassword = it },
          label = { Text(stringResource(string.auth_user_password)) },
          singleLine = true,
          modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
        )
        if (model.errorText.isNotBlank()) {
          Text(
            text = model.errorText,
            color = Color.Red
          )
        }
        SirenLoadingButton(
          onClick = { viewModel.obtainEvent(ProfileScreenEvent.OnAuthorizeButtonClick(userName, userPassword)) },
          text = stringResource(string.auth_sign_in_button),
          isLoading = model.isAuthProgress,
          enabled = !isLoading
        )
        SirenLoadingButton(
          onClick = { viewModel.obtainEvent(ProfileScreenEvent.OnRegistrationButtonClick(userName, userPassword)) },
          text = stringResource(string.auth_sign_up_button),
          isLoading = model.isRegProgress,
          enabled = !isLoading
        )
      }
    }
  }
}