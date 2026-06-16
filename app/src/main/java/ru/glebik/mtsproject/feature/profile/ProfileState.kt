package ru.glebik.mtsproject.feature.profile

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data class ProfileUiState(
    val nickname: String,
    val email: String,
) : UiState

sealed interface ProfileIntent : UiIntent {
    data object Back : ProfileIntent
    data object OpenRentals : ProfileIntent
    data object Logout : ProfileIntent
}

sealed interface ProfileEffect : UiEffect {
    data object NavigateBack : ProfileEffect
    data object NavigateToMain : ProfileEffect
    data object NavigateToOnboarding : ProfileEffect
}
