package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

enum class InfoBannerStyle {
    Info,
    Warning,
}

@Composable
fun AppInfoBanner(
    boldPrefix: String,
    message: String,
    modifier: Modifier = Modifier,
    style: InfoBannerStyle = InfoBannerStyle.Info,
) {
    val colors = when (style) {
        InfoBannerStyle.Info -> InfoBannerColors(
            background = AppTheme.colors.frame.active.copy(alpha = 0.2f),
            border = AppTheme.colors.frame.active,
            text = AppTheme.colors.frame.onActive,
        )

        InfoBannerStyle.Warning -> InfoBannerColors(
            background = AppTheme.colors.warning.background,
            border = AppTheme.colors.warning.border,
            text = AppTheme.colors.warning.text,
        )
    }

    val text = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = colors.text,
                fontWeight = FontWeight.Bold,
            )
        ) {
            append(boldPrefix)
        }
        append(" ")
        withStyle(SpanStyle(color = colors.text)) {
            append(message)
        }
    }

    Text(
        text = text,
        style = AppTheme.typography.body,
        modifier = modifier
            .clip(AppTheme.shapes.default)
            .background(colors.background)
            .border(
                width = 1.dp,
                color = colors.border,
                shape = AppTheme.shapes.default,
            )
            .padding(16.dp),
    )
}

private data class InfoBannerColors(
    val background: Color,
    val border: Color,
    val text: Color,
)
