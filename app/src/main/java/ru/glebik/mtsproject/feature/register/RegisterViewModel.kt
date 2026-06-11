package ru.glebik.mtsproject.feature.register

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.glebik.mtsproject.core.arch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor() :
    BaseViewModel<RegisterUiState, RegisterEffect, RegisterIntent>() {

    override fun initialState(): RegisterUiState = RegisterUiState()

    override fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.NicknameChanged -> {
                mutableState.value = mutableState.value.copy(nickname = intent.value)
            }

            is RegisterIntent.EmailChanged -> {
                mutableState.value = mutableState.value.copy(email = intent.value)
            }

            is RegisterIntent.PasswordChanged -> {
                mutableState.value = mutableState.value.copy(password = intent.value)
            }

            RegisterIntent.TogglePasswordVisibility -> {
                mutableState.value = mutableState.value.copy(
                    isPasswordVisible = !mutableState.value.isPasswordVisible
                )
            }

            RegisterIntent.Back -> navigateBack()
            RegisterIntent.Submit,
            RegisterIntent.Login -> navigateToMain()
        }
    }

    private fun navigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(RegisterEffect.NavigateBack)
        }
    }

    private fun navigateToMain() {
        viewModelScope.launchSafe {
            mutableEffect.emit(RegisterEffect.NavigateToMain)
        }
    }
}
