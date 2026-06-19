package ru.glebik.mtsproject.feature.cell_activation

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState
import ru.glebik.mtsproject.core.arch.util.ViewProperty

data class CellActivationUiState(
    val cellId: String = "",
    val cell: ViewProperty<CellActivationUiModel> = ViewProperty.Loading,
    val cardNumber: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val isSubmitting: Boolean = false,
    val submitError: String? = null,
) : UiState {

    companion object {
        const val CARD_NUMBER_MAX_LENGTH = CellActivationInputLimits.CARD_NUMBER_MAX_LENGTH
        const val EXPIRY_DATE_MAX_LENGTH = CellActivationInputLimits.EXPIRY_DATE_MAX_LENGTH
        const val CVV_MAX_LENGTH = CellActivationInputLimits.CVV_MAX_LENGTH
    }
}

sealed interface CellActivationIntent : UiIntent {
    data class Load(val cellId: String) : CellActivationIntent
    data class CardNumberChanged(val value: String) : CellActivationIntent
    data class ExpiryDateChanged(val value: String) : CellActivationIntent
    data class CvvChanged(val value: String) : CellActivationIntent
    data object Back : CellActivationIntent
    data object OpenCell : CellActivationIntent
}

sealed interface CellActivationEffect : UiEffect {
    data object NavigateBack : CellActivationEffect
    data object NavigateToMyRents : CellActivationEffect
}
