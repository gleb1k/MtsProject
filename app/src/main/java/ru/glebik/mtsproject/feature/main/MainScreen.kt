package ru.glebik.mtsproject.feature.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.ContainerColumn
import ru.glebik.mtsproject.ui.ContainerRow
import ru.glebik.mtsproject.ui.IconContainer
import ru.glebik.mtsproject.ui.theme.AppTheme


@Composable
fun MainScreen(

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            ContainerRow(
                contentPadding = PaddingValues(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    IconContainer(
                        iconRes = R.drawable.common_box_24,
                        iconColor = AppTheme.colors.icon.onBlue,
                        containerColor = AppTheme.colors.icon.blue
                    )
                    Column(
                        Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text("Мои аренды")
                        Text("Нет активных аренд")
                    }
                }

                Icon(
                    painterResource(R.drawable.rounded_arrow_forward_24),
                    contentDescription = "",
                    tint = AppTheme.colors.icon.onGray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        items(stubLockers) { locker ->
            LockerItem(locker)
        }
    }
}

@Composable
private fun LockerItem(
    item: LockerUiModel,
) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(item.name)
            Icon(
                painterResource(R.drawable.rounded_arrow_forward_24),
                contentDescription = "",
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painterResource(R.drawable.location_pin_24),
                contentDescription = "",
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.size(4.dp))
            Text(item.address)
        }
        Text(
            text = buildAnnotatedString {
                append("Доступно ячеек ")

                withStyle(
                    style = SpanStyle(color = AppTheme.colors.frame.onActive)
                ) {
                    append(item.currentAvailableCells.toString())
                }

                append(" из ${item.maxAvailableCells}")
            },
        )
    }
}