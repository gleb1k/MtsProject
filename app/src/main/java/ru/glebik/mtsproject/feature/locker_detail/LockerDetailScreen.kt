package ru.glebik.mtsproject.feature.locker_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.ui.common.AppBadge
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.AppStatCard
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.screen.ErrorScreen
import ru.glebik.mtsproject.ui.screen.LoaderScreen
import ru.glebik.mtsproject.ui.theme.AppTheme
import ru.glebik.mtsproject.ui.util.asString

@Composable
fun LockerDetailScreen(
    lockerId: Long,
    onNavigateBack: () -> Unit,
    onNavigateToCellActivation: (Int) -> Unit,
    viewModel: LockerDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(lockerId) {
        viewModel.handleIntent(LockerDetailIntent.Load(lockerId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                LockerDetailEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    when (val lockerState = state.locker) {
        is ViewProperty.Loading -> LoaderScreen()

        is ViewProperty.Content -> LockerDetailContent(
            locker = lockerState.content,
            onBackClick = { viewModel.handleIntent(LockerDetailIntent.Back) },
            onCellClick = onNavigateToCellActivation,
        )

        is ViewProperty.Error -> ErrorScreen(lockerState.errorMessage.asString())
    }
}

@Composable
private fun LockerDetailContent(
    locker: LockerDetailUiModel,
    onBackClick: () -> Unit,
    onCellClick: (Int) -> Unit,
) {
    val availableCells = locker.cells.filter { !it.isOccupied }
    val occupiedCells = locker.cells.filter { it.isOccupied }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = locker.name,
            subtitle = locker.address,
            subtitleIconRes = R.drawable.location_pin_24,
            onBackClick = onBackClick,
        )

        SummarySection(
            availableCount = locker.availableCount,
            occupiedCount = locker.occupiedCount,
            totalCount = locker.totalCount,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                AppBadge(
                    text = "Доступные ячейки",
                    backgroundColor = AppTheme.colors.frame.active.copy(alpha = 0.3f),
                    textColor = AppTheme.colors.frame.onActive,
                    verticalPadding = 6.dp,
                )
            }

            items(availableCells) { cell ->
                CellCard(
                    cell = cell,
                    onClick = { onCellClick(cell.number) },
                )
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
                AppBadge(
                    text = "Занятые ячейки",
                    backgroundColor = AppTheme.colors.icon.gray,
                    textColor = AppTheme.colors.text.secondary,
                    verticalPadding = 6.dp,
                )
            }

            items(occupiedCells) { cell ->
                CellCard(cell = cell)
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@Composable
private fun SummarySection(
    availableCount: Int,
    occupiedCount: Int,
    totalCount: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.frame.active.copy(alpha = 0.2f))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        AppStatCard(
            value = availableCount.toString(),
            label = "ДОСТУПНО",
            valueColor = AppTheme.colors.frame.primary,
            modifier = Modifier.weight(1f),
        )

        AppStatCard(
            value = occupiedCount.toString(),
            label = "ЗАНЯТО",
            valueColor = AppTheme.colors.text.secondary,
            modifier = Modifier.weight(1f),
        )

        AppStatCard(
            value = totalCount.toString(),
            label = "ВСЕГО",
            valueColor = AppTheme.colors.frame.primary,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun CellCard(
    cell: CellUiModel,
    onClick: (() -> Unit)? = null,
) {
    val style = cell.size.toStyle(AppTheme.colors.icon, cell.isOccupied)
    val titleColor = if (cell.isOccupied) {
        AppTheme.colors.text.secondary
    } else {
        AppTheme.colors.text.primary
    }
    val priceColor = if (cell.isOccupied) {
        AppTheme.colors.icon.onGray
    } else {
        AppTheme.colors.text.secondary
    }

    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconContainer(
                    iconRes = style.iconRes,
                    iconColor = style.iconColor,
                    containerColor = style.containerColor,
                )

                Column(
                    modifier = Modifier.padding(start = 12.dp),
                ) {
                    Text(
                        text = "Ячейка #${cell.number}",
                        style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                        color = titleColor,
                    )

                    Text(
                        text = "${cell.pricePerHour} ₽/час",
                        style = AppTheme.typography.body,
                        color = priceColor,
                        modifier = Modifier.padding(top = 4.dp),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    AppBadge(
                        text = if (cell.isOccupied) "Занята" else "Доступна",
                        backgroundColor = if (cell.isOccupied) {
                            AppTheme.colors.icon.gray
                        } else {
                            AppTheme.colors.frame.button
                        },
                        textColor = if (cell.isOccupied) {
                            AppTheme.colors.text.secondary
                        } else {
                            AppTheme.colors.frame.onButton
                        },
                    )
                }
            }

            AppBadge(
                text = cell.size.label,
                backgroundColor = style.badgeColor,
                textColor = style.badgeTextColor,
                verticalPadding = 6.dp,
            )
        }
    }
}
