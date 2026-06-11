package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppStatCard(
    value: String,
    label: String,
    valueColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(AppTheme.shapes.default)
            .background(AppTheme.colors.frame.surface)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = value,
            style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
            color = valueColor,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            style = AppTheme.typography.caption,
            color = AppTheme.colors.text.secondary,
            textAlign = TextAlign.Center,
        )
    }
}
