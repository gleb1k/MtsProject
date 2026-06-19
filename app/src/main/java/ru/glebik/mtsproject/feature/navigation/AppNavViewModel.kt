package ru.glebik.mtsproject.feature.navigation


import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.glebik.mtsproject.core.session.UserSession
import javax.inject.Inject

@HiltViewModel
class AppNavViewModel @Inject constructor(
    private val userSession: UserSession,
) : ViewModel() {

    fun startDestination(): NavKey {
        return if (userSession.isLoggedIn()) {
            MainNavKey()
        } else {
            OnboardingNavKey
        }
    }
}
