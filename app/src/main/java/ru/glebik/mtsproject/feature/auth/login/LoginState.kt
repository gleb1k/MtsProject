package ru.glebik.mtsproject.feature.auth.login

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data class LoginUiState(
    val nickname: String = "",
    val login: String = "",

    val isLoading: Boolean = false,
    val error: String? = null,
) : UiState

sealed interface LoginIntent : UiIntent {
    data class NicknameChanged(val value: String) : LoginIntent
    data class LoginChanged(val value: String) : LoginIntent
    data object Submit : LoginIntent
    data object Back : LoginIntent
}

sealed interface LoginEffect : UiEffect {
    data object NavigateBack : LoginEffect
    data object NavigateToMain : LoginEffect
}
