package com.adtarassov.siren.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adtarassov.siren.ui.theme.SirenSize.Big
import com.adtarassov.siren.ui.theme.SirenSize.Medium
import com.adtarassov.siren.ui.theme.SirenSize.Small

@Composable
fun SirenTheme(
  textSize: SirenSize = Medium,
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colors = if (darkTheme) {
    baseDarkPalette
  } else {
    baseLightPalette
  }

  val typography = SirenTypography(
    heading = TextStyle(
      fontSize = when (textSize) {
        Small -> 12.sp
        Medium -> 18.sp
        Big -> 32.sp
      },
      fontWeight = FontWeight.Normal
    ),
    body = TextStyle(
      fontSize = when (textSize) {
        Small -> 10.sp
        Medium -> 14.sp
        Big -> 18.sp
      },
      fontWeight = FontWeight.Normal,
    ),
    toolbar = TextStyle(
      fontSize = when (textSize) {
        Small -> 14.sp
        Medium -> 18.sp
        Big -> 20.sp
      },
      fontWeight = FontWeight.Normal
    ),
    caption = TextStyle(
      fontSize = when (textSize) {
        Small -> 10.sp
        Medium -> 12.sp
        Big -> 14.sp
      }
    )
  )

  val shapes = SirenShape(
    small = RoundedCornerShape(0.dp),
    medium = RoundedCornerShape(16.dp),
    big = RoundedCornerShape(20.dp)
  )

  CompositionLocalProvider(
    LocalSirenColors provides colors,
    LocalSirenTypography provides typography,
    LocalSirenShape provides shapes,
    content = content
  )
}