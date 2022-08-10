package com.adtarassov.siren.ui.screens.base

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.adtarassov.siren.ui.utils.Screens
import com.adtarassov.siren.ui.theme.SirenTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
  var startAnimate by remember {
    mutableStateOf(false)
  }
  val alphaAnimation = animateFloatAsState(
    targetValue = if (startAnimate) 1f else 0f,
    animationSpec = tween(3000)
  )
  LaunchedEffect(key1 = true) {
    startAnimate = true
    delay(4000)
    navController.navigate(Screens.MAIN_SCREEN)
  }
  Splash(alpha = alphaAnimation.value)
}

@Composable
fun Splash(alpha: Float) {
  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text(
      modifier = Modifier.alpha(alpha = alpha),
      fontSize = 30.sp,
      text = "Siren"
    )
  }
}

@Composable
@Preview(showBackground = true)
fun previewSplash() {
  SirenTheme {
    Splash(1f)
  }
}