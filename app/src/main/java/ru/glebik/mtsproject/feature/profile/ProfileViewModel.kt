package ru.glebik.mtsproject.feature.profile

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.session.UserSession
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userSession: UserSession,
) :
    BaseViewModel<ProfileUiState, ProfileEffect, ProfileIntent>() {

    override fun initialState(): ProfileUiState {
        val user = userSession.getUser()

        return ProfileUiState(
            nickname = user?.fullName.orEmpty(),
            email = user?.email.orEmpty()
        )
    }

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            ProfileIntent.Back -> navigateBack()
            ProfileIntent.OpenRentals -> navigateToMain()
            ProfileIntent.Logout -> logout()
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

    private fun logout() {
        userSession.clear()

        viewModelScope.launchSafe {
            mutableEffect.emit(ProfileEffect.NavigateToOnboarding)
        }
    }
}
