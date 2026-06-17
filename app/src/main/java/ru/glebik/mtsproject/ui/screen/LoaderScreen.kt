package ru.glebik.mtsproject.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun LoaderScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = AppTheme.colors.frame.primary,
        )
    }
}
