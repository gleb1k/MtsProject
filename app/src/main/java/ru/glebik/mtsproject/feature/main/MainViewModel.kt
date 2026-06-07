package ru.glebik.mtsproject.feature.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.main.data.LockersRepository
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LockersRepository
) : BaseViewModel<MainUiState, MainEffect, MainIntent>() {

    override fun initialState(): MainUiState = MainUiState()

    init {
        handleIntent(MainIntent.Load)
    }

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            MainIntent.Load -> loadLockers()
        }
    }

    private fun loadLockers() {
        viewModelScope.launchSafe {
            mutableState.value = mutableState.value.copy(
                lockers = ViewProperty.Loading
            )

            try {
                val data = repository.getLockers()

                mutableState.value = mutableState.value.copy(
                    lockers = ViewProperty.Content(data)
                )

            } catch (e: Exception) {
                mutableState.value = mutableState.value.copy(
                    lockers = ViewProperty.Error(
                        errorMessage = UiText.DynamicString("Ошибка загрузки"),
                        error = e
                    )
                )
            }
        }
    }
}