package ru.glebik.mtsproject.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun AppHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBackClick: (() -> Unit)? = null,
    subtitleIconRes: Int? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    bottomPadding: androidx.compose.ui.unit.Dp = 20.dp,
    bottomContent: @Composable (ColumnScope.() -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppTheme.colors.frame.primary)
            .statusBarsPadding()
            .padding(bottom = bottomPadding),
        horizontalAlignment = horizontalAlignment,
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.rounded_arrow_back_24),
                        contentDescription = null,
                        tint = AppTheme.colors.frame.onPrimary,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

            if (title.isNotBlank() || subtitle != null) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalAlignment = horizontalAlignment,
                ) {
                    if (title.isNotBlank()) {
                        Text(
                            text = title,
                            style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                            color = AppTheme.colors.frame.onPrimary,
                        )
                    }

                    if (subtitle != null) {
                        Spacer(modifier = Modifier.height(if (subtitleIconRes != null) 8.dp else 4.dp))

                        if (subtitleIconRes != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    painter = painterResource(subtitleIconRes),
                                    contentDescription = null,
                                    tint = AppTheme.colors.frame.onPrimary,
                                    modifier = Modifier.size(16.dp),
                                )

                                Text(
                                    text = subtitle,
                                    style = AppTheme.typography.body,
                                    color = AppTheme.colors.frame.onPrimary,
                                    modifier = Modifier.padding(start = 6.dp),
                                )
                            }
                        } else {
                            Text(
                                text = subtitle,
                                style = AppTheme.typography.body,
                                color = AppTheme.colors.frame.onPrimary,
                            )
                        }
                    }
                }
            }
        }

        bottomContent?.invoke(this)
    }
}
