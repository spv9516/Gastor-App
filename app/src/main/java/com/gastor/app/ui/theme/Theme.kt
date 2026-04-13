package com.gastor.app.ui.theme

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

private val CinematicDarkScheme = darkColorScheme(
    background = CoreBackground,
    surface = SurfaceCard,
    primary = AccentCyan,
    onPrimary = androidx.compose.ui.graphics.Color.Black,
    onBackground = TextWhite,
    onSurface = TextWhite,
    error = NeonRed
)

@Composable
fun GastorTheme(
    content: @Composable () -> Unit
) {
    // Forzamos "Dark Theme" cinemático
    MaterialTheme(
        colorScheme = CinematicDarkScheme,
        typography = Typography,
        content = content
    )
}