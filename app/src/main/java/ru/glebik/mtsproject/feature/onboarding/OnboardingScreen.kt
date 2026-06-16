package ru.glebik.mtsproject.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.common.OutlinedButton
import ru.glebik.mtsproject.ui.common.PrimaryButton
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun OnboardingScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToMain: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel(),
) {
    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                OnboardingEffect.NavigateToRegister -> onNavigateToRegister()
                OnboardingEffect.NavigateToMain -> onNavigateToMain()
                OnboardingEffect.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }

    OnboardingContent(
        onRegisterClick = { viewModel.handleIntent(OnboardingIntent.Register) },
        onLoginClick = { viewModel.handleIntent(OnboardingIntent.Login) },
        onLogoClick = { viewModel.handleIntent(OnboardingIntent.Main) }
    )
}

@Composable
private fun OnboardingContent(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onLogoClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            LogoBlock(onNavigateToMain = onLogoClick)

            Spacer(modifier = Modifier.height(32.dp))

            TitleBlock()

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Безопасное хранение ваших вещей",
                style = AppTheme.typography.body,
                color = AppTheme.colors.text.secondary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                PrimaryButton(
                    text = "Регистрация",
                    onClick = onRegisterClick,
                )

                OutlinedButton(
                    text = "Войти",
                    onClick = onLoginClick,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "МегаФон © 2026",
                style = AppTheme.typography.caption,
                color = AppTheme.colors.text.secondary,
                modifier = Modifier.padding(bottom = 24.dp),
            )
        }
    }
}

@Composable
private fun LogoBlock(
    onNavigateToMain: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(88.dp)
            .clip(AppTheme.shapes.default)
            .background(AppTheme.colors.frame.primary)
            .clickable {
                onNavigateToMain()
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.postomat_logo_48),
            contentDescription = null,
            tint = AppTheme.colors.frame.onPrimary,
            modifier = Modifier.size(48.dp),
        )
    }
}

@Composable
private fun TitleBlock() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Мегафон",
            style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.text.primary,
            textAlign = TextAlign.Center,
        )

        Text(
            text = "Постоматы",
            style = AppTheme.typography.header.copy(fontWeight = FontWeight.Bold),
            color = AppTheme.colors.frame.primary,
            textAlign = TextAlign.Center,
        )
    }
}
