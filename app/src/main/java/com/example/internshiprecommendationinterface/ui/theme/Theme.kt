package com.example.internshiprecommendationinterface.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.example.internshiprecommendationinterface.R

@Composable
private fun getLightColorScheme() = lightColorScheme(
    primary = colorResource(R.color.dark_purple),
    secondary = colorResource(R.color.light_purple),
    background = colorResource(R.color.background),
    surface = colorResource(R.color.white),
    onPrimary = colorResource(R.color.white),
    onSecondary = colorResource(R.color.dark_purple),
    onBackground = colorResource(R.color.black),
    onSurface = colorResource(R.color.black),
    tertiary = colorResource(R.color.green),
    error = colorResource(R.color.red)
)

@Composable
fun InternshipRecommendationInterfaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = getLightColorScheme()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}