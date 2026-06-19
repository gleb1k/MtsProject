package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.ui.theme.AppTheme

private val DangerColor = Color(0xFFEF4444)

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.default)
            .background(
                if (enabled && !loading) AppTheme.colors.frame.primary
                else AppTheme.colors.frame.primary.copy(alpha = 0.5f)
            )
            .clickable(
                enabled = enabled && !loading,
                onClick = onClick
            )
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = AppTheme.colors.frame.onPrimary,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = text,
                style = AppTheme.typography.button.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.frame.onPrimary,
            )
        }
    }
}

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.default)
            .border(
                width = 2.dp,
                color = AppTheme.colors.frame.primary,
                shape = AppTheme.shapes.default,
            )
            .background(AppTheme.colors.frame.surface)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = AppTheme.typography.button.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.frame.primary,
        )
    }
}

@Composable
fun PrimaryIconButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
    loading: Boolean = false,
    enabled: Boolean = true,
) {
    val isClickable = enabled && !loading

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.default)
            .background(
                if (isClickable) AppTheme.colors.frame.primary
                else AppTheme.colors.frame.primary.copy(alpha = 0.5f)
            )
            .clickable(
                enabled = isClickable,
                onClick = onClick,
            )
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = AppTheme.colors.frame.onPrimary,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp),
                )
            } else {
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = AppTheme.colors.frame.onPrimary,
                    modifier = Modifier.size(20.dp),
                )
            }

            Text(
                text = text,
                style = AppTheme.typography.button.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.frame.onPrimary,
            )
        }
    }
}

@Composable
fun DangerOutlinedButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.default)
            .border(
                width = 1.dp,
                color = DangerColor,
                shape = AppTheme.shapes.default,
            )
            .background(AppTheme.colors.frame.surface)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = DangerColor,
                modifier = Modifier.size(20.dp),
            )

            Text(
                text = text,
                style = AppTheme.typography.button.copy(fontWeight = FontWeight.Bold),
                color = DangerColor,
            )
        }
    }
}
