package ru.glebik.mtsproject.feature.rent_payment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.common.ContainerColumn
import ru.glebik.mtsproject.ui.common.IconContainer
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun RentPaymentScreen(
    onNavigateToSuccess: () -> Unit,
    viewModel: RentPaymentViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.handleIntent(RentPaymentIntent.Start)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RentPaymentEffect.NavigateToSuccess -> onNavigateToSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        ContainerColumn(
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            IconContainer(
                iconRes = R.drawable.credit_card_24,
                iconColor = AppTheme.colors.frame.primary,
                containerColor = AppTheme.colors.frame.active.copy(alpha = 0.2f),
            )

            Text(
                text = "Оплата",
                style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
                color = AppTheme.colors.text.primary,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "Обрабатываем платёж,\nпожалуйста подождите",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(36.dp),
                color = AppTheme.colors.frame.primary,
                strokeWidth = 3.dp,
            )
        }
    }
}
