package ru.glebik.mtsproject.feature.rent_detail

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState
import ru.glebik.mtsproject.core.arch.util.ViewProperty

data class RentDetailUiState(
    val rent: ViewProperty<RentDetailUiModel> = ViewProperty.Loading,
) : UiState

sealed interface RentDetailIntent : UiIntent {
    data class Load(val rentalId: String) : RentDetailIntent
    data object Back : RentDetailIntent
    data object EndRental : RentDetailIntent
}

sealed interface RentDetailEffect : UiEffect {
    data object NavigateBack : RentDetailEffect

    data class NavigateToRentCompletion(
        val rentalId: String,
        val cellNumber: Int,
    ) : RentDetailEffect
}
