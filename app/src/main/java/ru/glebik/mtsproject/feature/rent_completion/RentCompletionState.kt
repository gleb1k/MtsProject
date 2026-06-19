package ru.glebik.mtsproject.feature.rent_completion

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data class RentCompletionUiState(
    val cellNumber: Int = 0,
    val isClosing: Boolean = false,
    val closeError: String? = null,
) : UiState

sealed interface RentCompletionIntent : UiIntent {
    data class Load(
        val rentalId: String,
        val cellNumber: Int,
    ) : RentCompletionIntent

    data object Back : RentCompletionIntent
    data object ConfirmDoorClosed : RentCompletionIntent
}

sealed interface RentCompletionEffect : UiEffect {
    data object NavigateBack : RentCompletionEffect
    data object NavigateToPayment : RentCompletionEffect
}
