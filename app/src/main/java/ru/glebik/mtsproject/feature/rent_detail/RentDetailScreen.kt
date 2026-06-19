package ru.glebik.mtsproject.feature.rent_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.time.DateTime
import ru.glebik.mtsproject.feature.locker_detail.toStyle
import ru.glebik.mtsproject.feature.my_rents.RentUiModel.RentStatus
import ru.glebik.mtsproject.feature.my_rents.calculateCurrentCost
import ru.glebik.mtsproject.feature.my_rents.formatRentalDurationCompact
import ru.glebik.mtsproject.feature.my_rents.rememberRentNowMillis
import ru.glebik.mtsproject.feature.my_rents.toBadgeText
import ru.glebik.mtsproject.ui.common.AppBadge
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.AppInfoBanner
import ru.glebik.mtsproject.ui.common.InfoBannerStyle
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.DangerIconButton
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.screen.ErrorScreen
import ru.glebik.mtsproject.ui.screen.LoaderScreen
import ru.glebik.mtsproject.ui.theme.AppTheme
import ru.glebik.mtsproject.ui.util.asString

@Composable
fun RentDetailScreen(
    rentalId: String,
    onNavigateBack: () -> Unit,
    viewModel: RentDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(rentalId) {
        viewModel.handleIntent(RentDetailIntent.Load(rentalId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RentDetailEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    when (val rentState = state.rent) {
        is ViewProperty.Loading -> LoaderScreen()

        is ViewProperty.Content -> RentDetailContent(
            rent = rentState.content,
            isEndingRental = state.isEndingRental,
            onBackClick = { viewModel.handleIntent(RentDetailIntent.Back) },
            onEndRentalClick = { viewModel.handleIntent(RentDetailIntent.EndRental) },
        )

        is ViewProperty.Error -> ErrorScreen(rentState.errorMessage.asString())
    }
}

@Composable
private fun RentDetailContent(
    rent: RentDetailUiModel,
    isEndingRental: Boolean,
    onBackClick: () -> Unit,
    onEndRentalClick: () -> Unit,
) {
    val nowMillis = rememberRentNowMillis()
    val now = remember(nowMillis) { DateTime.fromMillis(nowMillis) }
    val rentalDuration = formatRentalDurationCompact(
        startedAt = rent.startedAt,
        now = now,
    )
    val currentCost = calculateCurrentCost(
        pricePerHour = rent.pricePerHour,
        startedAt = rent.startedAt,
        now = now,
    )
    val cellStyle = rent.cellSize.toStyle(AppTheme.colors.icon, isOccupied = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = "Детали аренды",
            subtitle = "Ячейка #${rent.cellNumber}",
            onBackClick = onBackClick,
            bottomPadding = 24.dp,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            StatusCard(status = rent.status)

            RentalInfoCard(
                rent = rent,
                cellStyle = cellStyle,
            )

            MetricsCard(
                rentalDuration = rentalDuration,
                currentCost = currentCost,
            )

            AppInfoBanner(
                boldPrefix = "Важно:",
                message = "При нажатии кнопки «Закончить аренду» ячейка откроется. " +
                    "Достаньте вещи и закройте дверь физически. Оплата произойдёт автоматически.",
                style = InfoBannerStyle.Warning,
            )

            Spacer(modifier = Modifier.height(4.dp))
        }

        DangerIconButton(
            text = "Закончить аренду",
            onClick = onEndRentalClick,
            loading = isEndingRental,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 16.dp)
                .navigationBarsPadding(),
        )
    }
}

@Composable
private fun StatusCard(status: RentStatus) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Статус аренды",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
            )

            AppBadge(
                text = status.toBadgeText(),
                backgroundColor = AppTheme.colors.text.primary,
                textColor = AppTheme.colors.frame.surface,
                verticalPadding = 6.dp,
            )
        }
    }
}

@Composable
private fun RentalInfoCard(
    rent: RentDetailUiModel,
    cellStyle: ru.glebik.mtsproject.feature.locker_detail.CellStyle,
) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconContainer(
                iconRes = cellStyle.iconRes,
                iconColor = cellStyle.iconColor,
                containerColor = cellStyle.containerColor,
            )

            Column(
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(
                    text = "Ячейка #${rent.cellNumber}",
                    style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.text.primary,
                )

                AppBadge(
                    text = rent.cellSizeLabel,
                    backgroundColor = cellStyle.badgeColor,
                    textColor = cellStyle.badgeTextColor,
                    verticalPadding = 4.dp,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                painter = painterResource(R.drawable.location_pin_24),
                contentDescription = null,
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp),
            )

            Column(
                modifier = Modifier.padding(start = 8.dp),
            ) {
                Text(
                    text = rent.lockerName,
                    style = AppTheme.typography.body.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colors.text.primary,
                )

                Text(
                    text = rent.lockerAddress,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.text.secondary,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }

        Text(
            text = "${rent.pricePerHour} ₽/час",
            style = AppTheme.typography.body,
            color = AppTheme.colors.text.primary,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.credit_card_24),
                contentDescription = null,
                tint = AppTheme.colors.icon.onGray,
                modifier = Modifier.size(20.dp),
            )

            Text(
                text = formatCardDisplay(rent.maskedPan),
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Composable
private fun MetricsCard(
    rentalDuration: String,
    currentCost: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AppTheme.shapes.default)
            .border(
                width = 1.dp,
                color = AppTheme.colors.frame.divider,
                shape = AppTheme.shapes.default,
            )
            .background(AppTheme.colors.frame.active.copy(alpha = 0.15f))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.clock_24),
                    contentDescription = null,
                    tint = AppTheme.colors.frame.primary,
                    modifier = Modifier.size(20.dp),
                )

                Text(
                    text = "Время аренды",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.text.secondary,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }

            Text(
                text = rentalDuration,
                style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.frame.primary,
            )
        }

        HorizontalDivider(color = AppTheme.colors.frame.primary)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Текущая стоимость",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
            )

            Text(
                text = "$currentCost ₽",
                style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.frame.primary,
            )
        }
    }
}
