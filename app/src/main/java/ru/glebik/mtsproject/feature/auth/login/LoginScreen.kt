package ru.glebik.mtsproject.feature.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.glebik.mtsproject.ui.common.AppHeader
import ru.glebik.mtsproject.ui.common.AppTextField
import ru.glebik.mtsproject.ui.common.PrimaryButton
import ru.glebik.mtsproject.ui.theme.AppTheme


@Composable
fun LoginScreen(
    onNavigateBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                LoginEffect.NavigateBack -> onNavigateBack()
                LoginEffect.NavigateToMain -> onNavigateToMain()
            }
        }
    }

    LoginContent(
        state = state,
        onBackClick = { viewModel.handleIntent(LoginIntent.Back) },
        onNicknameChange = { viewModel.handleIntent(LoginIntent.NicknameChanged(it)) },
        onLoginChange = { viewModel.handleIntent(LoginIntent.LoginChanged(it)) },
        onSubmitClick = { viewModel.handleIntent(LoginIntent.Submit) },
    )
}

@Composable
private fun LoginContent(
    state: LoginUiState,
    onBackClick: () -> Unit,
    onNicknameChange: (String) -> Unit,
    onLoginChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.surface),
    ) {
        AppHeader(
            title = "Вход",
            subtitle = "Войдите в аккаунт",
            onBackClick = onBackClick,
            bottomPadding = 16.dp,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .navigationBarsPadding(),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            AppTextField(
                label = "Никнейм",
                value = state.nickname,
                onValueChange = onNicknameChange,
                placeholder = "Ваш никнейм",
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                label = "Email / Телефон",
                value = state.login,
                onValueChange = onLoginChange,
                placeholder = "you@example.com или +7...",
                keyboardType = KeyboardType.Text,
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (!state.error.isNullOrBlank()) {
                Text(
                    text = state.error,
                    color = AppTheme.colors.frame.error,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            PrimaryButton(
                text = "Войти",
                onClick = onSubmitClick,
                loading = state.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
