package com.example.betschallenge.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = RedPrimary,
    onPrimary = BlackPrimary,
    secondary = LightGray,
    onSecondary = BlackPrimary,
    background = LightGray,
    onBackground = WhitePrimary,
    surface = BlackPrimary,
    onSurface = WhitePrimary
)

private val LightColorScheme = lightColorScheme(
    primary = RedPrimary,
    onPrimary = WhitePrimary,
    secondary = BlackPrimary,
    onSecondary = WhitePrimary,
    background = WhitePrimary,
    onBackground = BlackPrimary,
    surface = WhitePrimary,
    onSurface = BlackPrimary
)


@Composable
fun BetsChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}