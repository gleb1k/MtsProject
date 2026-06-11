package ru.glebik.mtsproject.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

object AppTheme {

    val colors: Colors
        @Composable
        get() = LocalCustomColors.current

    val typography: AppTypography
        @Composable
        get() = LocalCustomTypography.current

    val shapes: AppShapes
        @Composable
        get() = LocalCustomShapes.current

}

val LocalCustomColors = staticCompositionLocalOf<Colors> {
    error("No colors provided")
}

val LocalCustomTypography = staticCompositionLocalOf<AppTypography> {
    error("No colors provided")
}

val LocalCustomShapes = staticCompositionLocalOf<AppShapes> {
    error("No shapes provided")
}