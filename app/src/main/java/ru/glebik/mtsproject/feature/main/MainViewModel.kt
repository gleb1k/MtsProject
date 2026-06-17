package ru.glebik.mtsproject.feature.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.main.data.LockersRepository
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LockersRepository,
    private val userSession: UserSession,
) : BaseViewModel<MainUiState, MainEffect, MainIntent>() {

    override fun initialState(): MainUiState {
        val user = userSession.getUser()

        return MainUiState(
            nickName = user?.fullName.orEmpty(),
        )
    }

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
            mutableState.update {
                it.copy(lockers = ViewProperty.Loading)
            }

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
