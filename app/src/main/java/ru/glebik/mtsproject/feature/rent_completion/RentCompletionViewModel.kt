package ru.glebik.mtsproject.feature.rent_completion

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.feature.rents_api.domain.CloseRentalUseCase
import javax.inject.Inject

@HiltViewModel
class RentCompletionViewModel @Inject constructor(
    private val closeRentalUseCase: CloseRentalUseCase,
) : BaseViewModel<RentCompletionUiState, RentCompletionEffect, RentCompletionIntent>() {

    private var rentalId: String? = null

    override fun initialState(): RentCompletionUiState = RentCompletionUiState()

    override fun handleIntent(intent: RentCompletionIntent) {
        when (intent) {
            is RentCompletionIntent.Load -> load(intent.rentalId, intent.cellNumber)
            RentCompletionIntent.Back -> navigateBack()
            RentCompletionIntent.ConfirmDoorClosed -> confirmDoorClosed()
        }
    }

    private fun load(rentalId: String, cellNumber: Int) {
        this.rentalId = rentalId
        mutableState.update {
            it.copy(cellNumber = cellNumber, closeError = null)
        }
    }

    private fun confirmDoorClosed() {
        if (mutableState.value.isClosing) return

        val id = rentalId ?: return

        viewModelScope.launchSafe {
            mutableState.update {
                it.copy(isClosing = true, closeError = null)
            }

            closeRentalUseCase(id)
                .onSuccess {
                    Log.d("RentCompletionVM", "Аренда завершена: ${it.id}")
                    mutableState.update { it.copy(isClosing = false) }
                    mutableEffect.emit(RentCompletionEffect.NavigateToPayment)
                }
                .onFailure { error ->
                    Log.e("RentCompletionVM", "Ошибка завершения аренды", error)
                    mutableState.update {
                        it.copy(
                            isClosing = false,
                            closeError = "Не удалось завершить аренду",
                        )
                    }
                }
        }
    }

    private fun navigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(RentCompletionEffect.NavigateBack)
        }
    }
}
