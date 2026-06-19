package ru.glebik.mtsproject.feature.rent_completion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.common.PrimaryButton
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun RentCompletionScreen(
    rentalId: String,
    cellNumber: Int,
    onNavigateBack: () -> Unit,
    onNavigateToPayment: () -> Unit,
    viewModel: RentCompletionViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(rentalId, cellNumber) {
        viewModel.handleIntent(RentCompletionIntent.Load(rentalId, cellNumber))
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RentCompletionEffect.NavigateBack -> onNavigateBack()
                RentCompletionEffect.NavigateToPayment -> onNavigateToPayment()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background),
    ) {
        AppHeader(
            title = "Завершение аренды",
            subtitle = "Ячейка #$cellNumber",
            bottomPadding = 24.dp,
        )

        ContainerColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            IconContainer(
                iconRes = R.drawable.lock_24,
                iconColor = AppTheme.colors.frame.primary,
                containerColor = AppTheme.colors.frame.active.copy(alpha = 0.2f),
            )

            Text(
                text = "Ячейка открыта",
                style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.text.primary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Пожалуйста, достаньте ваши вещи и закройте дверь ячейки физически.",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Время продолжает идти до закрытия двери",
                style = AppTheme.typography.body.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.warning.text,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "После закрытия двери произойдёт автоматическое списание средств",
                style = AppTheme.typography.body,
                color = AppTheme.colors.frame.onActive,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(AppTheme.shapes.default)
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.frame.active,
                        shape = AppTheme.shapes.default,
                    )
                    .background(AppTheme.colors.frame.active.copy(alpha = 0.15f))
                    .padding(16.dp),
            )

            if (state.closeError != null) {
                Text(
                    text = state.closeError,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.frame.error,
                )
            }

            PrimaryButton(
                text = "Я закрыл дверь",
                onClick = { viewModel.handleIntent(RentCompletionIntent.ConfirmDoorClosed) },
                loading = state.isClosing,
            )
        }
    }
}
