package com.example.betschallenge.presentation.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.betschallenge.presentation.ui.theme.BetsChallengeTheme
import com.example.betschallenge.presentation.ui.theme.RedSecundary
import kotlinx.coroutines.delay


@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    BetsChallengeTheme {
        SplashScreen(onNavigateToMain = {})
    }
}

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit = {}) {
    val animationDuration = 2000
    val animationDelay = 1000



    val backgroundColorAnimation by animateColorAsState(
        targetValue = RedSecundary,
        animationSpec = tween(durationMillis = animationDuration, delayMillis = animationDelay),
        label = ""
    )


    LaunchedEffect(key1 = true) {
        delay(animationDuration + animationDelay.toLong())
        onNavigateToMain()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColorAnimation),
        contentAlignment = Alignment.Center
    ) {
        LottieRawAnimation(onNavigateToMain)
    }
}

@Composable
fun LottieRawAnimation(onAnimationFinished: () -> Unit) {
    val composition by rememberLottieComposition(  LottieCompositionSpec.Url("https://lottie.host/15e33d90-5753-42e5-9753-dfda9795a366/SnWuNstGbf.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1 // Or a specific number of iterations
    )
    val isPlaying by remember { derivedStateOf { progress < 1f } } // Check if animation is in progress
    LottieAnimation(composition = composition, progress = progress)
    // Text("Animation is playing: $isPlaying")
    LaunchedEffect(key1 = isPlaying) {
        if (!isPlaying) {
            // Animation finished, perform your action here
            onAnimationFinished()
        }
    }
}