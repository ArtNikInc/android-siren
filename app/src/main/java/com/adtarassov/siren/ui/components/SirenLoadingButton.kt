package com.adtarassov.siren.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adtarassov.siren.R.string
import com.adtarassov.siren.ui.theme.SirenTheme

@Composable
fun SirenLoadingButton(
  onClick: () -> Unit,
  text: String,
  isLoading: Boolean,
  enabled: Boolean,
) {
  OutlinedButton(
    modifier = Modifier
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .fillMaxWidth(),
    onClick = onClick,
    border = BorderStroke(1.dp, SirenTheme.colors.mainText),
    enabled = enabled
  ) {
    if (isLoading) {
      CircularProgressIndicator(
        color = SirenTheme.colors.mainText,
        strokeWidth = 2.dp,
        modifier = Modifier.size(18.dp)
      )
    } else {
      Text(color = SirenTheme.colors.mainText, text = text)
    }
  }

}

@Preview
@Composable
fun PreviewSirenLoadingButton() {
  SirenLoadingButton(
    text = "Test",
    isLoading = false,
    onClick = {

    },
    enabled = true
  )
}

@Preview
@Composable
fun PreviewLoadingSirenLoadingButton() {
  SirenLoadingButton(
    text = "Test",
    isLoading = true,
    onClick = {

    },
    enabled = true
  )
}