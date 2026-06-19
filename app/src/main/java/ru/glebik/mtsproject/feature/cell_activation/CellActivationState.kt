package ru.glebik.mtsproject.feature.cell_activation

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.feature.locker_detail.CellSize

data class CellActivationUiModel(
    val cellNumber: Int,
    val lockerName: String,
    val lockerAddress: String,
    val pricePerHour: Int,
    val size: CellSize,
)

data class CellActivationUiState(
    val cell: ViewProperty<CellActivationUiModel> = ViewProperty.Loading,
    val cardNumber: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
) : UiState

sealed interface CellActivationIntent : UiIntent {
    data class Load(val lockerId: String, val cellNumber: Int) : CellActivationIntent
    data class CardNumberChanged(val value: String) : CellActivationIntent
    data class ExpiryDateChanged(val value: String) : CellActivationIntent
    data class CvvChanged(val value: String) : CellActivationIntent
    data object Back : CellActivationIntent
    data object OpenCell : CellActivationIntent
}

sealed interface CellActivationEffect : UiEffect {
    data object NavigateBack : CellActivationEffect
}
