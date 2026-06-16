package ru.glebik.mtsproject.feature.auth.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import ru.glebik.mtsproject.ui.common.LinkedTextFooter
import ru.glebik.mtsproject.ui.common.PrimaryButton
import ru.glebik.mtsproject.ui.theme.AppTheme

@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                RegisterEffect.NavigateBack -> onNavigateBack()
                RegisterEffect.NavigateToMain -> onNavigateToMain()
            }
        }
    }

    RegisterContent(
        state = state,
        onBackClick = { viewModel.handleIntent(RegisterIntent.Back) },
        onNicknameChange = { viewModel.handleIntent(RegisterIntent.NicknameChanged(it)) },
        onEmailChange = { viewModel.handleIntent(RegisterIntent.EmailChanged(it)) },
        onPhoneChange = { viewModel.handleIntent(RegisterIntent.PhoneChanged(it)) },
        onSubmitClick = { viewModel.handleIntent(RegisterIntent.Submit) },
        onLoginClick = { viewModel.handleIntent(RegisterIntent.Login) },
    )
}

@Composable
private fun RegisterContent(
    state: RegisterUiState,
    onBackClick: () -> Unit,
    onNicknameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSubmitClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.frame.surface),
    ) {
        AppHeader(
            title = "Регистрация",
            subtitle = "Создайте новый аккаунт",
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
                label = "Email",
                value = state.email,
                onValueChange = onEmailChange,
                placeholder = "you@example.com",
                keyboardType = KeyboardType.Email,
            )

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                label = "Телефон",
                value = state.phone,
                onValueChange = onPhoneChange,
                placeholder = "+7 999 999 99 99",
                keyboardType = KeyboardType.Phone,
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
                text = "Зарегистрироваться",
                onClick = onSubmitClick,
                loading = state.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            LinkedTextFooter(
                prefix = "Уже есть аккаунт? ",
                linkText = "Войти",
                linkTag = "login",
                onLinkClick = onLoginClick,
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
