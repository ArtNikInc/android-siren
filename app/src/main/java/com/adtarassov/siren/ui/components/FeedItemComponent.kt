package com.adtarassov.siren.ui.components

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adtarassov.siren.ui.models.SirenFeedUiModel

@Composable
fun FeedItemComponent(
  model: SirenFeedUiModel
) {
  Card(
    modifier = Modifier
      .fillMaxWidth(),
    elevation = 2.dp,
    backgroundColor = MaterialTheme.colors.primaryVariant,
    shape = MaterialTheme.shapes.large
  ) {
    Box(modifier = Modifier.height(30.dp))
  }
}