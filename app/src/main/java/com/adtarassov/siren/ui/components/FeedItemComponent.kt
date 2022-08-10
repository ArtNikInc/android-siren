package com.adtarassov.siren.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import com.adtarassov.siren.ui.theme.SirenTheme
import com.adtarassov.siren.ui.theme.SirenTheme.typography
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
fun FeedItemComponent(
  model: SirenFeedUiModel,
) {
  Card(
    backgroundColor = SirenTheme.colors.bgMain,
    elevation = 4.dp,
    shape = SirenTheme.shapes.medium,
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
        model.authorImage?.let { drawable ->
          Image(
            painter = rememberDrawablePainter(drawable = drawable),
            contentDescription = model.authorName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .padding(end = 4.dp)
              .size(32.dp)
              .clip(CircleShape)
              .background(SirenTheme.colors.bgMinor)
          )
        }
        Text(
          text = model.authorName,
          style = typography.heading,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }
      Column(
        modifier = Modifier
          .padding(top = 2.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = model.header,
          style = typography.heading,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        Text(
          text = model.body,
          style = typography.body,
          color = SirenTheme.colors.minorText,
          modifier = Modifier.padding(top = 2.dp),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}