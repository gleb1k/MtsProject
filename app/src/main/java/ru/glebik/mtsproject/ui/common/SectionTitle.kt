package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    iconRes: Int? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconRes != null) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = AppTheme.colors.text.primary,
                modifier = Modifier.size(20.dp),
            )
        }

        Text(
            text = title,
            style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.text.primary,
            modifier = Modifier.padding(start = if (iconRes != null) 8.dp else 0.dp),
        )
    }
}
