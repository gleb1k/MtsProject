package ru.glebik.mtsproject.feature.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.locker.domain.GetLockersUseCase
import ru.glebik.mtsproject.feature.locker.data.model.LockerResponse
import ru.glebik.mtsproject.feature.locker.domain.model.Locker
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLockersUseCase: GetLockersUseCase,
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
                val result = getLockersUseCase()

                val data = result.getOrThrow().map { lockerResponse -> lockerResponse.toUiModel() }

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

    private fun Locker.toUiModel(): LockerUiModel {
        return LockerUiModel(
            id = id.hashCode().toLong(),
            name = title,
            address = address,
            currentAvailableCells = 0,
            maxAvailableCells = 0,
        )
    }
}
