package ru.glebik.mtsproject.feature.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockersUseCase
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell.*
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
                Log.e("MainVM", "Ошибка загрузки локеров", e)
                mutableState.update {
                    it.copy(
                        lockers = ViewProperty.Error(
                            errorMessage = UiText.DynamicString("Ошибка загрузки"),
                            error = e
                        )
                    )
                }
            }
        }
    }

    private fun Locker.toUiModel(): LockerUiModel {
        val availableCells = cells.count { it.status == Status.AVAILABLE }

        return LockerUiModel(
            id = id.hashCode().toLong(),
            name = title,
            address = address,
            currentAvailableCells = availableCells,
            maxAvailableCells = cells.size,
        )
    }
}
