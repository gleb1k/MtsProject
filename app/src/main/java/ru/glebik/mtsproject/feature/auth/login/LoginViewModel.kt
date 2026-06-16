package ru.glebik.mtsproject.feature.auth.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.feature.auth.domain.usecase.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginEffect, LoginIntent>() {

    override fun initialState(): LoginUiState = LoginUiState()

    override fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.NicknameChanged -> {
                mutableState.update {
                    it.copy(nickname = intent.value, error = null)
                }
            }

            is LoginIntent.LoginChanged -> {
                mutableState.update {
                    it.copy(login = intent.value, error = null)
                }
            }

            LoginIntent.Submit -> login()
            LoginIntent.Back -> navigateBack()
        }
    }

    private fun login() {
        val state = mutableState.value

        when {
            state.nickname.isBlank() -> {
                mutableState.update { it.copy(error = "Введите никнейм") }
                return
            }
            state.nickname.length < 3 -> {
                mutableState.update { it.copy(error = "Никнейм должен быть не менее 3 символов") }
                return
            }

            state.login.isBlank() -> {
                mutableState.update { it.copy(error = "Введите email или телефон") }
                return
            }
        }

        viewModelScope.launch {
            mutableState.update { it.copy(isLoading = true, error = null) }

            loginUseCase(
                nickname = state.nickname,
                login = state.login,
            )
                .onSuccess {
                    mutableState.update { it.copy(isLoading = false) }
                    mutableEffect.emit(LoginEffect.NavigateToMain)
                }
                .onFailure { error ->
                    mutableState.update { state ->
                        state.copy(
                            isLoading = false,
                            error = error.message ?: "Ошибка"
                        )
                    }
                }
        }
    }

    private fun navigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(LoginEffect.NavigateBack)
        }
    }
}
