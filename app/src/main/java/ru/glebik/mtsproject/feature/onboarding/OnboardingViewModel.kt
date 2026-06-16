package ru.glebik.mtsproject.feature.onboarding

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.glebik.mtsproject.core.arch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() :
    BaseViewModel<OnboardingUiState, OnboardingEffect, OnboardingIntent>() {

    override fun initialState(): OnboardingUiState = OnboardingUiState()

    override fun handleIntent(intent: OnboardingIntent) {
        when (intent) {
            OnboardingIntent.Register -> navigateToRegister()
            OnboardingIntent.Login -> navigateToLogin()
            OnboardingIntent.Main -> navigateToMain()
        }
    }

    private fun navigateToRegister() {
        viewModelScope.launchSafe {
            mutableEffect.emit(OnboardingEffect.NavigateToRegister)
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launchSafe {
            mutableEffect.emit(OnboardingEffect.NavigateToLogin)
        }
    }

    private fun navigateToMain() {
        viewModelScope.launchSafe {
            mutableEffect.emit(OnboardingEffect.NavigateToMain)
        }
    }
}
