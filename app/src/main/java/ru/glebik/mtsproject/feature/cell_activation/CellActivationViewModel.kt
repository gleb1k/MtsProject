package ru.glebik.mtsproject.feature.cell_activation

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.cell_activation.data.CellActivationRepository
import javax.inject.Inject

@HiltViewModel
class CellActivationViewModel @Inject constructor(
    private val repository: CellActivationRepository,
) : BaseViewModel<CellActivationUiState, CellActivationEffect, CellActivationIntent>() {

    override fun initialState(): CellActivationUiState = CellActivationUiState()

    override fun handleIntent(intent: CellActivationIntent) {
        when (intent) {
            is CellActivationIntent.Load -> loadCell(intent.lockerId, intent.cellNumber)

            is CellActivationIntent.CardNumberChanged -> {
                mutableState.update { it.copy(cardNumber = intent.value) }
            }

            is CellActivationIntent.ExpiryDateChanged -> {
                mutableState.update { it.copy(expiryDate = intent.value) }
            }

            is CellActivationIntent.CvvChanged -> {
                mutableState.update { it.copy(cvv = intent.value) }
            }

            CellActivationIntent.Back -> navigateBack()
            CellActivationIntent.OpenCell -> navigateBack()
        }
    }

    private fun loadCell(lockerId: String, cellNumber: Int) {
        viewModelScope.launchSafe {
            mutableState.update { it.copy(cell = ViewProperty.Loading) }

            try {
                val data = repository.getCellActivation(lockerId, cellNumber)

                mutableState.update {
                    it.copy(
                        cell = ViewProperty.Content(data),
                    )
                }
            } catch (e: Exception) {
                Log.e("CellActivationVM", "Ошибка загрузки ячейки", e)
                mutableState.update {
                    it.copy(
                        cell = ViewProperty.Error(
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
            mutableEffect.emit(CellActivationEffect.NavigateBack)
        }
    }
}
