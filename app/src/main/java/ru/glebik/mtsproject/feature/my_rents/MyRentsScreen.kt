package ru.glebik.mtsproject.feature.my_rents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.screen.ErrorScreen
import ru.glebik.mtsproject.ui.screen.LoaderScreen
import ru.glebik.mtsproject.ui.theme.AppTheme
import ru.glebik.mtsproject.ui.util.asString

@Composable
fun MyRentsScreen(
    onNavigateBack: () -> Unit,
    viewModel: MyRentsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                MyRentsEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    when (val rentsState = state.rents) {
        is ViewProperty.Loading -> {
            LoaderScreen()
        }

        is ViewProperty.Content -> {
            MyRentsContent(
                rents = rentsState.content,
                onBackClick = viewModel::onNavigateBack,
            )
        }

        is ViewProperty.Error -> {
            ErrorScreen(rentsState.errorMessage.asString())
        }
    }
}

@Composable
private fun MyRentsContent(
    rents: List<RentUiModel>,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.surface),
    ) {
        AppHeader(
            title = "Мои аренды",
            onBackClick = onBackClick,
            bottomPadding = 24.dp,
        )

        if (rents.isEmpty()) {
            EmptyRentsList()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(rents) { rent ->
                    RentItem(rent = rent)
                }
            }
        }
    }
}

@Composable
private fun EmptyRentsList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = AppTheme.colors.icon.gray,
                    shape = AppTheme.shapes.default
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.common_box_24),
                contentDescription = null,
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(32.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Нет активных аренд",
            style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.text.primary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Здесь появятся ваши текущие и завершенные аренды",
            style = AppTheme.typography.body,
            color = AppTheme.colors.text.secondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
    }
}

@Composable
private fun RentItem(rent: RentUiModel) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = rent.lockerTitle,
                style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.text.primary,
            )

            Text(
                text = when (rent.status) {
                    RentUiModel.RentStatus.ACTIVE -> "Активна"
                    RentUiModel.RentStatus.WAITING_CLOSE -> "Ждет закрытия"
                    RentUiModel.RentStatus.PAYMENT -> "Ждет оплаты"
                    RentUiModel.RentStatus.COMPLETED -> "Завершена"
                    RentUiModel.RentStatus.CANCELLED -> "Отменена"
                    RentUiModel.RentStatus.OVERDUE -> "Просрочена"
                },
                style = AppTheme.typography.caption,
                color = when (rent.status) {
                    RentUiModel.RentStatus.ACTIVE -> AppTheme.colors.frame.onActive
                    else -> AppTheme.colors.text.secondary
                },
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painterResource(R.drawable.location_pin_24),
                contentDescription = "",
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = rent.lockerAddress,
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painterResource(R.drawable.common_box_24),
                contentDescription = "",
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Ячейка #${rent.cellNumber}",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                painterResource(R.drawable.common_box_24),
                contentDescription = "",
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "${rent.startTime} - ${rent.endTime ?: "В процессе"}",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
            )
        }
    }
}
