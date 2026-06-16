package ru.glebik.mtsproject.feature.auth.register

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data class RegisterUiState(
    val nickname: String = "",
    val email: String = "",
    val phone: String = "",

    val isLoading: Boolean = false,
    val error: String? = null,
) : UiState

sealed interface RegisterIntent : UiIntent {
    data class NicknameChanged(val value: String) : RegisterIntent
    data class EmailChanged(val value: String) : RegisterIntent
    data class PhoneChanged(val value: String) : RegisterIntent
    data object Back : RegisterIntent
    data object Submit : RegisterIntent
    data object Login : RegisterIntent
}

sealed interface RegisterEffect : UiEffect {
    data object NavigateBack : RegisterEffect
    data object NavigateToMain : RegisterEffect
}
