package ru.glebik.mtsproject.feature.locker_detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockerByIdUseCase
import ru.glebik.mtsproject.feature.locker_cell_api.domain.GetLockerCellsUseCase
import javax.inject.Inject

@HiltViewModel
class LockerDetailViewModel @Inject constructor(
    private val getLockerByIdUseCase: GetLockerByIdUseCase,
    private val getLockerCellsUseCase: GetLockerCellsUseCase,
) : BaseViewModel<LockerDetailUiState, LockerDetailEffect, LockerDetailIntent>() {

    override fun initialState(): LockerDetailUiState = LockerDetailUiState()

    override fun handleIntent(intent: LockerDetailIntent) {
        when (intent) {
            is LockerDetailIntent.Load -> loadLocker(intent.lockerId)
            LockerDetailIntent.Back -> navigateBack()
        }
    }

    private fun loadLocker(lockerId: String) {
        viewModelScope.launchSafe {
            mutableState.update {
                it.copy(locker = ViewProperty.Loading)
            }

            try {
                val locker = getLockerByIdUseCase(lockerId).getOrThrow()
                val cells = getLockerCellsUseCase(lockerId).getOrThrow()
                val data = locker.toDetailUiModel(cells)

                mutableState.update {
                    it.copy(locker = ViewProperty.Content(data))
                }
            } catch (e: Exception) {
                Log.e("LockerDetailVM", "Ошибка загрузки локера", e)
                mutableState.update {
                    it.copy(
                        locker = ViewProperty.Error(
                            errorMessage = UiText.DynamicString("Ошибка загрузки"),
                            error = e,
                        )
                    )
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(LockerDetailEffect.NavigateBack)
        }
    }
}
