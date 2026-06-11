package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppInfoBanner(
    boldPrefix: String,
    message: String,
    modifier: Modifier = Modifier,
) {
    val text = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = AppTheme.colors.frame.onActive,
                fontWeight = FontWeight.Bold,
            )
        ) {
            append(boldPrefix)
        }
        append(" ")
        withStyle(SpanStyle(color = AppTheme.colors.frame.onActive)) {
            append(message)
        }
    }

    Text(
        text = text,
        style = AppTheme.typography.body,
        modifier = modifier
            .clip(AppTheme.shapes.default)
            .background(AppTheme.colors.frame.active.copy(alpha = 0.2f))
            .border(
                width = 1.dp,
                color = AppTheme.colors.frame.active,
                shape = AppTheme.shapes.default,
            )
            .padding(16.dp),
    )
}
