package ru.glebik.mtsproject.feature.rent_detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockerByIdUseCase
import ru.glebik.mtsproject.feature.locker_cell_api.domain.GetLockerCellByIdUseCase
import ru.glebik.mtsproject.feature.payment.domain.usecase.GetPaymentMethodByIdUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.GetRentsUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental
import javax.inject.Inject

@HiltViewModel
class RentDetailViewModel @Inject constructor(
    private val getRentsUseCase: GetRentsUseCase,
    private val getLockerCellByIdUseCase: GetLockerCellByIdUseCase,
    private val getLockerByIdUseCase: GetLockerByIdUseCase,
    private val getPaymentMethodByIdUseCase: GetPaymentMethodByIdUseCase,
) : BaseViewModel<RentDetailUiState, RentDetailEffect, RentDetailIntent>() {

    override fun initialState(): RentDetailUiState = RentDetailUiState()

    override fun handleIntent(intent: RentDetailIntent) {
        when (intent) {
            is RentDetailIntent.Load -> loadRent(intent.rentalId)
            RentDetailIntent.Back -> navigateBack()
            RentDetailIntent.EndRental -> endRental()
        }
    }

    private fun loadRent(rentalId: String) {
        viewModelScope.launchSafe {
            mutableState.update {
                it.copy(rent = ViewProperty.Loading)
            }

            try {
                val rental = getRentsUseCase().getOrThrow()
                    .find { it.id == rentalId }
                    ?: throw NoSuchElementException("Аренда не найдена")

                val uiModel = rental.toDetailUiModel()

                mutableState.value = mutableState.value.copy(
                    rent = ViewProperty.Content(uiModel),
                )
            } catch (e: Exception) {
                Log.e("RentDetailVM", "Ошибка загрузки аренды", e)
                mutableState.update {
                    it.copy(
                        rent = ViewProperty.Error(
                            errorMessage = UiText.DynamicString("Ошибка загрузки аренды"),
                            error = e,
                        )
                    )
                }
            }
        }
    }

    private suspend fun Rental.toDetailUiModel(): RentDetailUiModel {
        val cell = getLockerCellByIdUseCase(cellId).getOrNull()
        val locker = cell?.let { getLockerByIdUseCase(it.stationId).getOrNull() }
        val maskedPan = paymentMethodId?.let { methodId ->
            getPaymentMethodByIdUseCase(methodId).getOrNull()?.maskedPan
        }
        return toDetailUiModel(cell = cell, locker = locker, maskedPan = maskedPan)
    }

    private fun endRental() {
        val rent = (mutableState.value.rent as? ViewProperty.Content)?.content ?: return

        viewModelScope.launchSafe {
            mutableEffect.emit(
                RentDetailEffect.NavigateToRentCompletion(
                    rentalId = rent.id,
                    cellNumber = rent.cellNumber,
                )
            )
        }
    }

    private fun navigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(RentDetailEffect.NavigateBack)
        }
    }
}
