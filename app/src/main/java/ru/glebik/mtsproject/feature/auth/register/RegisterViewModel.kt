package ru.glebik.mtsproject.feature.auth.register

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.feature.auth.domain.usecase.RegisterUseCase
import ru.glebik.mtsproject.feature.auth.domain.validator.EmailValidator
import ru.glebik.mtsproject.feature.auth.domain.validator.PhoneValidator
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : BaseViewModel<RegisterUiState, RegisterEffect, RegisterIntent>() {

    override fun initialState(): RegisterUiState = RegisterUiState()

    override fun handleIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.NicknameChanged -> {
                mutableState.update {
                    it.copy(
                        nickname = intent.value,
                        error = null,
                    )
                }
            }

            is RegisterIntent.EmailChanged -> {
                mutableState.update {
                    it.copy(
                        email = intent.value,
                        error = null,
                    )
                }
            }

            is RegisterIntent.PhoneChanged -> {
                mutableState.update {
                    it.copy(
                        phone = intent.value,
                        error = null,
                    )
                }
            }

            RegisterIntent.Submit -> register()
            RegisterIntent.Back -> navigateBack()
            RegisterIntent.Login -> navigateToMain()
        }
    }

    private fun register() {
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

            !EmailValidator.isValid(state.email) -> {
                mutableState.update { it.copy(error = "Некорректный email") }
                return
            }

            state.phone.isBlank() -> {
                mutableState.update { it.copy(error = "Введите номер телефона") }
                return
            }

            !PhoneValidator.isValid(state.phone) -> {
                mutableState.update { it.copy(error = "Некорректный номер телефона") }
                return
            }
        }

        viewModelScope.launch {
            mutableState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }
            registerUseCase(
                nickname = state.nickname,
                email = state.email,
                phone = state.phone,
            ).onSuccess {
                mutableState.update {
                    it.copy(isLoading = false)
                }
                mutableEffect.emit(
                    RegisterEffect.NavigateToMain,
                )
            }.onFailure { throwable ->
                mutableState.update {
                    it.copy(
                        isLoading = false,
                        error = throwable.message ?: "Unknown error",
                    )
                }
            }
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
