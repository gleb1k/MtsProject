package ru.glebik.mtsproject.feature.cell_activation

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.feature.locker_detail.CellStyle
import ru.glebik.mtsproject.feature.locker_detail.toStyle
import ru.glebik.mtsproject.ui.common.AppBadge
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.AppInfoBanner
import ru.glebik.mtsproject.ui.common.AppSectionTitle
import ru.glebik.mtsproject.ui.common.AppTextField
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.common.PrimaryIconButton
import ru.glebik.mtsproject.ui.screen.ErrorScreen
import ru.glebik.mtsproject.ui.screen.LoaderScreen
import ru.glebik.mtsproject.ui.theme.AppTheme
import ru.glebik.mtsproject.ui.util.asString

@Composable
fun CellActivationScreen(
    cellId: String,
    onNavigateBack: () -> Unit,
    onNavigateToMyRents: () -> Unit,
    viewModel: CellActivationViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(cellId) {
        viewModel.handleIntent(CellActivationIntent.Load(cellId))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                CellActivationEffect.NavigateBack -> onNavigateBack()
                CellActivationEffect.NavigateToMyRents -> onNavigateToMyRents()
            }
        }
    }

    when (val cellState = state.cell) {
        is ViewProperty.Loading -> LoaderScreen()

        is ViewProperty.Content -> CellActivationContent(
            cell = cellState.content,
            cardNumber = state.cardNumber,
            expiryDate = state.expiryDate,
            cvv = state.cvv,
            isSubmitting = state.isSubmitting,
            submitError = state.submitError,
            onBackClick = { viewModel.handleIntent(CellActivationIntent.Back) },
            onCardNumberChange = { viewModel.handleIntent(CellActivationIntent.CardNumberChanged(it)) },
            onExpiryDateChange = { viewModel.handleIntent(CellActivationIntent.ExpiryDateChanged(it)) },
            onCvvChange = { viewModel.handleIntent(CellActivationIntent.CvvChanged(it)) },
            onOpenCellClick = { viewModel.handleIntent(CellActivationIntent.OpenCell) },
        )

        is ViewProperty.Error -> ErrorScreen(cellState.errorMessage.asString())
    }
}

@Composable
private fun CellActivationContent(
    cell: CellActivationUiModel,
    cardNumber: String,
    expiryDate: String,
    cvv: String,
    isSubmitting: Boolean,
    submitError: String?,
    onBackClick: () -> Unit,
    onCardNumberChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onOpenCellClick: () -> Unit,
) {
    val style = cell.size.toStyle(AppTheme.colors.icon, isOccupied = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = "Активация ячейки",
            subtitle = "Введите данные карты для оплаты",
            onBackClick = onBackClick,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            CellSummaryCard(
                cell = cell,
                style = style,
            )

            PaymentCard(
                cardNumber = cardNumber,
                expiryDate = expiryDate,
                cvv = cvv,
                onCardNumberChange = onCardNumberChange,
                onExpiryDateChange = onExpiryDateChange,
                onCvvChange = onCvvChange,
            )

            AppInfoBanner(
                boldPrefix = "Обратите внимание:",
                message = "После нажатия кнопки дверь ячейки откроется. Аренда начнётся после физического закрытия двери.",
            )

            if (submitError != null) {
                Text(
                    text = submitError,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.frame.primary,
                )
            }

            PrimaryIconButton(
                text = if (isSubmitting) "Сохранение..." else "Открыть ячейку",
                iconRes = R.drawable.unlock_24,
                onClick = onOpenCellClick,
                loading = isSubmitting,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CellSummaryCard(
    cell: CellActivationUiModel,
    style: CellStyle,
) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.Top,
            ) {
                IconContainer(
                    iconRes = style.iconRes,
                    iconColor = style.iconColor,
                    containerColor = style.containerColor,
                )

                Column(
                    modifier = Modifier.padding(start = 12.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "Ячейка #${cell.cellNumber}",
                            style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                            color = AppTheme.colors.text.primary,
                        )

                        AppBadge(
                            text = cell.size.label,
                            backgroundColor = style.badgeColor,
                            textColor = style.badgeTextColor,
                            verticalPadding = 6.dp,
                        )
                    }

                    Text(
                        text = cell.lockerName,
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.text.secondary,
                        modifier = Modifier.padding(top = 8.dp),
                    )

                    Text(
                        text = cell.lockerAddress,
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.text.secondary,
                        modifier = Modifier.padding(top = 4.dp),
                    )

                    Text(
                        text = "${cell.pricePerHour} ₽/час",
                        style = AppTheme.typography.title.copy(fontWeight = FontWeight.Bold),
                        color = AppTheme.colors.frame.primary,
                        modifier = Modifier.padding(top = 12.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun PaymentCard(
    cardNumber: String,
    expiryDate: String,
    cvv: String,
    onCardNumberChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
) {
    ContainerColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        AppSectionTitle(
            title = "Данные карты",
            iconRes = R.drawable.credit_card_24,
        )

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            label = "Номер карты",
            value = cardNumber,
            onValueChange = onCardNumberChange,
            placeholder = "1234 5678 9012 3456",
            keyboardType = KeyboardType.Number,
            visualTransformation = CardNumberVisualTransformation,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppTextField(
                label = "Срок действия",
                value = expiryDate,
                onValueChange = onExpiryDateChange,
                placeholder = "MM/YY",
                keyboardType = KeyboardType.Number,
                visualTransformation = ExpiryDateVisualTransformation,
                modifier = Modifier.weight(1f),
            )

            AppTextField(
                label = "CVV",
                value = cvv,
                onValueChange = onCvvChange,
                placeholder = "123",
                keyboardType = KeyboardType.NumberPassword,
                isPassword = true,
                modifier = Modifier.weight(1f),
            )
        }
    }
}
