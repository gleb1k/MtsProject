package ru.glebik.mtsproject.feature.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.glebik.mtsproject.core.arch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() :
    BaseViewModel<ProfileUiState, ProfileEffect, ProfileIntent>() {

    override fun initialState(): ProfileUiState = ProfileUiState()

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.Back -> navigateBack()
            ProfileIntent.OpenRentals -> navigateToMain()
            ProfileIntent.Logout -> navigateToOnboarding()
        }
    }

    private fun navigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(ProfileEffect.NavigateBack)
        }
    }

    private fun navigateToMain() {
        viewModelScope.launchSafe {
            mutableEffect.emit(ProfileEffect.NavigateToMain)
        }
    }

    private fun navigateToOnboarding() {
        viewModelScope.launchSafe {
            mutableEffect.emit(ProfileEffect.NavigateToOnboarding)
        }
    }
}
