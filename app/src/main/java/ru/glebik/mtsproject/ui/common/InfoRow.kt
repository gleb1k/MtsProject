package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun IconInfoRow(
    iconRes: Int,
    iconColor: Color,
    containerColor: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconContainer(
            iconRes = iconRes,
            iconColor = iconColor,
            containerColor = containerColor,
        )

        Column(
            modifier = Modifier.padding(start = 12.dp),
        ) {
            Text(
                text = label,
                style = AppTheme.typography.caption,
                color = AppTheme.colors.text.secondary,
            )

            Text(
                text = value,
                style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.text.primary,
            )
        }
    }
}
