package ru.glebik.mtsproject.feature.main

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.ContainerRow
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.screen.ErrorScreen
import ru.glebik.mtsproject.ui.screen.LoaderScreen
import ru.glebik.mtsproject.ui.theme.AppTheme
import ru.glebik.mtsproject.ui.util.asString


@Composable
fun MainScreen(
    onNavigateToProfile: () -> Unit,
    onNavigateToLockerDetail: (Long) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {

    val state = viewModel.state.collectAsStateWithLifecycle().value

    when (val lockersState = state.lockers) {

        is ViewProperty.Loading -> {
            LoaderScreen()
        }

        is ViewProperty.Content -> {
            MainContent(
                lockers = lockersState.content,
                onNavigateToProfile = onNavigateToProfile,
                onNavigateToLockerDetail = onNavigateToLockerDetail,
            )
        }

        is ViewProperty.Error -> {
            ErrorScreen(lockersState.errorMessage.asString())
        }
    }
}

@Composable
private fun MainContent(
    lockers: List<LockerUiModel>,
    onNavigateToProfile: () -> Unit,
    onNavigateToLockerDetail: (Long) -> Unit,
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
            MainTopBar(onNavigateToProfile = onNavigateToProfile)
        }

        item {
            Header()
        }

        items(lockers) { locker ->
            LockerItem(
                item = locker,
                onClick = { onNavigateToLockerDetail(locker.id) },
            )
        }
    }
}

@Composable
private fun MainTopBar(
    onNavigateToProfile: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Постоматы",
            style = AppTheme.typography.header,
            color = AppTheme.colors.text.primary,
        )

        IconButton(onClick = onNavigateToProfile) {
            Icon(
                painter = painterResource(R.drawable.person_24),
                contentDescription = null,
                tint = AppTheme.colors.frame.primary,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Composable
private fun Header() {
    ContainerRow(
        contentPadding = PaddingValues(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
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
                Text(
                    text = "Мои аренды",
                    style = AppTheme.typography.title
                )

                Text(
                    text = "Нет активных аренд",
                    style = AppTheme.typography.caption
                )
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

@Composable
private fun LockerItem(
    item: LockerUiModel,
    onClick: () -> Unit,
) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            Text(
                text = item.address,
                style = AppTheme.typography.body
            )
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
            style = AppTheme.typography.body
        )
    }
}