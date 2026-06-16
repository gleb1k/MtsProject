package ru.glebik.mtsproject.feature.onboarding

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data class OnboardingUiState(
    val isLoading: Boolean = false,
) : UiState

sealed interface OnboardingIntent : UiIntent {
    data object Register : OnboardingIntent
    data object Login : OnboardingIntent

    data object Main : OnboardingIntent
}

sealed interface OnboardingEffect : UiEffect {
    data object NavigateToRegister : OnboardingEffect
    data object NavigateToMain : OnboardingEffect
    data object NavigateToLogin : OnboardingEffect
}
