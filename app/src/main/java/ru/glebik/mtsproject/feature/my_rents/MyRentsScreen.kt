package ru.glebik.mtsproject.feature.my_rents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.time.DateTime
import ru.glebik.mtsproject.feature.locker_detail.toStyle
import ru.glebik.mtsproject.feature.my_rents.RentUiModel.RentStatus
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
    onNavigateToRentDetail: (String) -> Unit,
    viewModel: MyRentsViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                MyRentsEffect.NavigateBack -> onNavigateBack()
                is MyRentsEffect.NavigateToRentDetail -> onNavigateToRentDetail(effect.rentalId)
            }
        }
    }

    when (val rentsState = state.rents) {
        is ViewProperty.Loading -> LoaderScreen()

        is ViewProperty.Content -> MyRentsContent(
            rents = rentsState.content,
            onBackClick = viewModel::onNavigateBack,
            onRentClick = viewModel::onRentClick,
        )

        is ViewProperty.Error -> ErrorScreen(rentsState.errorMessage.asString())
    }
}

@Composable
private fun MyRentsContent(
    rents: List<RentUiModel>,
    onBackClick: () -> Unit,
    onRentClick: (String) -> Unit,
) {
    val nowMillis = rememberRentNowMillis()
    val activeRents = rents.filter { it.isActive }
    val inactiveRents = rents.filter { !it.isActive }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = "Мои аренды",
            subtitle = "Все аренды",
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
                if (activeRents.isNotEmpty()) {
                    item {
                        SectionTitle("Активные")
                    }

                    items(activeRents, key = { it.id }) { rent ->
                        RentItem(
                            rent = rent,
                            nowMillis = nowMillis,
                            onClick = { onRentClick(rent.id) },
                        )
                    }
                }

                if (inactiveRents.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(if (activeRents.isNotEmpty()) 8.dp else 0.dp))
                        SectionTitle("Завершённые")
                    }

                    items(inactiveRents, key = { it.id }) { rent ->
                        RentItem(
                            rent = rent,
                            nowMillis = nowMillis,
                            onClick = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
        color = AppTheme.colors.text.secondary,
        modifier = Modifier.padding(bottom = 4.dp),
    )
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
            text = "Нет аренд",
            style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.text.primary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Здесь появятся ваши текущие и завершённые аренды",
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
    onClick: (() -> Unit)?,
) {
    val now = remember(nowMillis) { DateTime.fromMillis(nowMillis) }
    val displayStatus = if (rent.isActive) {
        rent.status
    } else {
        RentStatus.COMPLETED
    }
    val badgeStyle = displayStatus.toBadgeColors()
    val cellStyle = rent.cellSize.toStyle(AppTheme.colors.icon, isOccupied = false)
    val titleColor = if (rent.isActive) {
        AppTheme.colors.text.primary
    } else {
        AppTheme.colors.text.secondary
    }

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
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (rent.isActive) 1f else 0.75f)
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Ячейка #${rent.cellNumber}",
                style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                color = titleColor,
            )

            AppBadge(
                text = displayStatus.toBadgeText(),
                backgroundColor = badgeStyle.first,
                textColor = badgeStyle.second,
                verticalPadding = 6.dp,
            )
        }

        AppBadge(
            text = rent.cellSize.label,
            backgroundColor = cellStyle.badgeColor,
            textColor = cellStyle.badgeTextColor,
            verticalPadding = 4.dp,
            modifier = Modifier.padding(top = 8.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        RentDetailRow(
            iconRes = R.drawable.location_pin_24,
            text = rent.lockerAddress,
            muted = !rent.isActive,
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (rent.isActive) {
            RentDetailRow(
                iconRes = R.drawable.clock_24,
                text = "Время аренды: $rentalDuration",
            )

            Spacer(modifier = Modifier.height(8.dp))

            RentDetailRow(
                iconRes = R.drawable.credit_card_24,
                text = "Текущая стоимость: $currentCost ₽",
            )
        } else {
            RentDetailRow(
                iconRes = R.drawable.clock_24,
                text = "Статус: ${displayStatus.toBadgeText()}",
                muted = true,
            )
        }
    }
}

@Composable
private fun RentStatus.toBadgeColors(): Pair<Color, Color> {
    return when (this) {
        RentStatus.ACTIVE -> AppTheme.colors.text.primary to AppTheme.colors.frame.surface

        RentStatus.WAITING_CLOSE,
        RentStatus.PAYMENT -> AppTheme.colors.warning.background to AppTheme.colors.warning.text

        RentStatus.OVERDUE -> AppTheme.colors.frame.error.copy(alpha = 0.15f) to AppTheme.colors.frame.error

        RentStatus.COMPLETED,
        RentStatus.CANCELLED -> AppTheme.colors.icon.gray to AppTheme.colors.text.secondary
    }
}

@Composable
private fun RentDetailRow(
    iconRes: Int,
    text: String,
    muted: Boolean = false,
) {
    val textColor = if (muted) {
        AppTheme.colors.text.secondary.copy(alpha = 0.8f)
    } else {
        AppTheme.colors.text.secondary
    }

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
            color = textColor,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}
