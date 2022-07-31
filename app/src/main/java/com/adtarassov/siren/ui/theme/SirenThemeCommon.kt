package com.adtarassov.siren.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class SirenColors(
  val mainText: Color,
  val bgMain: Color,
  val minorText: Color,
  val bgMinor: Color,
  val tintColor: Color,
  val mainOnControlText: Color,
  val bgControlMain: Color,
  val minorOnControlText: Color,
  val bgControlMinor: Color,
  val errorColor: Color
)

data class SirenTypography(
  val heading: TextStyle,
  val body: TextStyle,
  val toolbar: TextStyle,
  val caption: TextStyle
)

data class SirenShape(
  val small: RoundedCornerShape,
  val medium: RoundedCornerShape,
  val big: RoundedCornerShape
)

object SirenTheme {
  val colors: SirenColors
    @Composable
    get() = LocalSirenColors.current

  val typography: SirenTypography
    @Composable
    get() = LocalSirenTypography.current

  val shapes: SirenShape
    @Composable
    get() = LocalSirenShape.current
}

enum class SirenSize {
  Small, Medium, Big
}

val LocalSirenColors = staticCompositionLocalOf<SirenColors> {
  error("No colors provided")
}

val LocalSirenTypography = staticCompositionLocalOf<SirenTypography> {
  error("No font provided")
}

val LocalSirenShape = staticCompositionLocalOf<SirenShape> {
  error("No shapes provided")
}