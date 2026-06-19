package ru.glebik.mtsproject.feature.cell_activation

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockerByIdUseCase
import ru.glebik.mtsproject.feature.locker_cell_api.domain.GetLockerCellByIdUseCase
import ru.glebik.mtsproject.feature.payment.domain.usecase.CreatePaymentMethodUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.CreateRentalUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.StartRentalUseCase
import javax.inject.Inject

@HiltViewModel
class CellActivationViewModel @Inject constructor(
    private val getLockerCellByIdUseCase: GetLockerCellByIdUseCase,
    private val getLockerByIdUseCase: GetLockerByIdUseCase,
    private val createPaymentMethodUseCase: CreatePaymentMethodUseCase,
    private val createRentalUseCase: CreateRentalUseCase,
    private val startRentalUseCase: StartRentalUseCase,
) : BaseViewModel<CellActivationUiState, CellActivationEffect, CellActivationIntent>() {

    override fun initialState(): CellActivationUiState = CellActivationUiState()

    override fun handleIntent(intent: CellActivationIntent) {
        when (intent) {
            is CellActivationIntent.Load -> loadCell(intent.cellId)

            is CellActivationIntent.CardNumberChanged -> {
                mutableState.update {
                    it.copy(
                        cardNumber = filterCardNumberDigits(intent.value),
                        submitError = null,
                    )
                }
            }

            is CellActivationIntent.ExpiryDateChanged -> {
                mutableState.update {
                    it.copy(
                        expiryDate = filterExpiryDateDigits(intent.value),
                        submitError = null,
                    )
                }
            }

            is CellActivationIntent.CvvChanged -> {
                mutableState.update {
                    it.copy(
                        cvv = filterCvvDigits(intent.value),
                        submitError = null,
                    )
                }
            }

            CellActivationIntent.Back -> navigateBack()
            CellActivationIntent.OpenCell -> createPaymentMethodAndOpenCell()
        }
    }

    private fun loadCell(cellId: String) {
        viewModelScope.launchSafe {
            mutableState.update {
                it.copy(cellId = cellId, cell = ViewProperty.Loading)
            }

            try {
                val cell = getLockerCellByIdUseCase(cellId).getOrThrow()
                val locker = getLockerByIdUseCase(cell.stationId).getOrThrow()
                val data = cell.toActivationUiModel(locker)

                mutableState.update {
                    it.copy(cell = ViewProperty.Content(data))
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

    private fun createPaymentMethodAndOpenCell() {
        val state = mutableState.value
        if (state.isSubmitting) return

        val cell = (state.cell as? ViewProperty.Content)?.content
        if (cell == null || state.cellId.isBlank()) {
            mutableState.update {
                it.copy(submitError = "Данные ячейки не загружены")
            }
            return
        }

        val maskedPan = formatMaskedPan(state.cardNumber)
        if (maskedPan == null) {
            mutableState.update {
                it.copy(submitError = "Введите номер карты")
            }
            return
        }

        viewModelScope.launchSafe {
            mutableState.update {
                it.copy(isSubmitting = true, submitError = null)
            }

            try {
                val paymentMethod = createPaymentMethodUseCase(
                    provider = PAYMENT_PROVIDER,
                    maskedPan = maskedPan,
                    token = "",
                    isVerified = true,
                ).getOrThrow()

                val rental = createRentalUseCase(
                    cellId = state.cellId,
                    pricePerHour = cell.pricePerHour,
                    paymentMethodId = paymentMethod.id,
                ).getOrThrow()

                startRentalUseCase(rental.id).getOrThrow()

                mutableState.update { it.copy(isSubmitting = false) }
                mutableEffect.emit(CellActivationEffect.NavigateToMyRents)
            } catch (error: Exception) {
                Log.e("CellActivationVM", "Ошибка активации ячейки", error)
                mutableState.update {
                    it.copy(
                        isSubmitting = false,
                        submitError = error.message ?: "Ошибка активации ячейки",
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
