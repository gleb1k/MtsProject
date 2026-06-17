package ru.glebik.mtsproject.feature.my_rents

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState
import ru.glebik.mtsproject.core.arch.util.ViewProperty

data class MyRentsUiState(
    val rents: ViewProperty<List<RentUiModel>> = ViewProperty.Loading,
) : UiState

sealed interface MyRentsIntent : UiIntent {
    data object Load : MyRentsIntent
}

sealed interface MyRentsEffect : UiEffect {
    data object NavigateBack : MyRentsEffect
}
