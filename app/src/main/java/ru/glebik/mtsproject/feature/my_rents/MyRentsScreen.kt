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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.core.time.DateTime
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.ui.common.AppBadge
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
        is ViewProperty.Loading -> LoaderScreen()

        is ViewProperty.Content -> MyRentsContent(
            rents = rentsState.content,
            onBackClick = viewModel::onNavigateBack,
        )

        is ViewProperty.Error -> ErrorScreen(rentsState.errorMessage.asString())
    }
}

@Composable
private fun MyRentsContent(
    rents: List<RentUiModel>,
    onBackClick: () -> Unit,
) {
    val nowMillis = rememberCurrentTimeMillis()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = "Мои аренды",
            subtitle = "Активные ячейки",
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
                items(rents, key = { it.id }) { rent ->
                    RentItem(
                        rent = rent,
                        nowMillis = nowMillis,
                    )
                }
            }
        }
    }
}

@Composable
private fun rememberCurrentTimeMillis(
    updateIntervalMillis: Long = 1_000L,
): Long {
    var nowMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(updateIntervalMillis) {
        while (true) {
            nowMillis = System.currentTimeMillis()
            delay(updateIntervalMillis)
        }
    }

    return nowMillis
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
                    shape = AppTheme.shapes.default,
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
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun RentItem(
    rent: RentUiModel,
    nowMillis: Long,
) {
    val now = remember(nowMillis) { DateTime.fromMillis(nowMillis) }

    val rentalDuration = formatRentalDuration(
        startedAt = rent.startedAt,
        now = now,
    )
    val currentCost = calculateCurrentCost(
        pricePerHour = rent.pricePerHour,
        startedAt = rent.startedAt,
        now = now,
    )

    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Ячейка #${rent.cellNumber}",
                style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.text.primary,
            )

            AppBadge(
                text = rent.status.toBadgeText(),
                backgroundColor = AppTheme.colors.text.primary,
                textColor = AppTheme.colors.frame.surface,
                verticalPadding = 6.dp,
            )
        }

        Text(
            text = rent.cellSizeLabel,
            style = AppTheme.typography.body,
            color = AppTheme.colors.text.secondary,
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        RentDetailRow(
            iconRes = R.drawable.location_pin_24,
            text = rent.lockerAddress,
        )

        Spacer(modifier = Modifier.height(8.dp))

        RentDetailRow(
            iconRes = R.drawable.clock_24,
            text = "Время аренды: $rentalDuration",
        )

        Spacer(modifier = Modifier.height(8.dp))

        RentDetailRow(
            iconRes = R.drawable.credit_card_24,
            text = "Текущая стоимость: $currentCost ₽",
        )
    }
}

@Composable
private fun RentDetailRow(
    iconRes: Int,
    text: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = AppTheme.colors.icon.onGray,
            modifier = Modifier.size(20.dp),
        )

        Text(
            text = text,
            style = AppTheme.typography.body,
            color = AppTheme.colors.text.secondary,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}

private fun RentUiModel.RentStatus.toBadgeText(): String {
    return when (this) {
        RentUiModel.RentStatus.ACTIVE -> "Активно"
        RentUiModel.RentStatus.WAITING_CLOSE -> "Ждет закрытия"
        RentUiModel.RentStatus.PAYMENT -> "Ждет оплаты"
        RentUiModel.RentStatus.COMPLETED -> "Завершена"
        RentUiModel.RentStatus.CANCELLED -> "Отменена"
        RentUiModel.RentStatus.OVERDUE -> "Просрочена"
    }
}
