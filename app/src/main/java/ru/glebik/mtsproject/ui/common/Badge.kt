package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    verticalPadding: androidx.compose.ui.unit.Dp = 4.dp,
) {
    Box(
        modifier = modifier
            .clip(AppTheme.shapes.default)
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = verticalPadding),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
            color = textColor,
        )
    }
}
