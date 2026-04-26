package ru.glebik.mtsproject.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

object AppTheme {

    val colors: Colors
        @Composable
        get() = LocalCustomColors.current
}

val LocalCustomColors = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}
