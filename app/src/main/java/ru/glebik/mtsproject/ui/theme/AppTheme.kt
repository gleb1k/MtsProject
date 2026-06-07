package ru.glebik.mtsproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    providedValues: Array<ProvidedValue<*>> = emptyArray(),
    content: @Composable () -> Unit,
) {
    val colors: Colors = when {
        darkTheme -> DarkColorPalette
        else -> LightColorPalette
    }

    val typography: AppTypography = appTypography

    CompositionLocalProvider(
        *providedValues,
        LocalCustomColors provides colors,
        LocalCustomTypography provides typography,
        content = content
    )
}