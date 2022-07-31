package com.adtarassov.siren.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.unit.dp
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.theme.SirenTheme

private const val EXPAND_ANIMATION_DURATION_MS = 300

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FeedItemComponent(
  model: SirenFeedUiModel,
  onExpandClick: (SirenFeedUiModel) -> Unit,
) {
  val expanded = model.isExpanded
  val transitionState = remember {
    MutableTransitionState(expanded).apply {
      targetState = !expanded
    }
  }
  val transition = updateTransition(transitionState, label = "")
  val cardPaddingHorizontal by transition.animateDp({
    tween(durationMillis = EXPAND_ANIMATION_DURATION_MS)
  }, label = "EXPAND_ANIMATION_DURATION") {
    if (expanded) 100.dp else 60.dp
  }
  Card(
    backgroundColor = SirenTheme.colors.bgMain,
    elevation = 12.dp,
    shape = SirenTheme.shapes.medium,
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        if (!transition.isRunning) {
          onExpandClick.invoke(model)
        }
      }
  ) {
    Text("1232131323123")
    ExpandableContent(visible = model.isExpanded)
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
  visible: Boolean = true,
  initialVisibility: Boolean = false,
) {
  val enterTransition = remember {
    expandVertically(
      expandFrom = Alignment.Top,
      animationSpec = tween(EXPAND_ANIMATION_DURATION_MS)
    ) + fadeIn(
      initialAlpha = 0.3f,
      animationSpec = tween(EXPAND_ANIMATION_DURATION_MS)
    )
  }
  val exitTransition = remember {
    shrinkVertically(
      // Expand from the top.
      shrinkTowards = Alignment.Top,
      animationSpec = tween(EXPAND_ANIMATION_DURATION_MS)
    ) + fadeOut(
      // Fade in with the initial alpha of 0.3f.
      animationSpec = tween(EXPAND_ANIMATION_DURATION_MS)
    )
  }
  AnimatedVisibility(
    visible = visible,
    initiallyVisible = initialVisibility,
    enter = enterTransition,
    exit = exitTransition
  ) {
    Box(
      modifier = Modifier
        .background(Color.Red)
        .height(50.dp)
    ) {
    }
  }
}