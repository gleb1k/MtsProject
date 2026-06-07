package ru.glebik.mtsproject.feature.main

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState
import ru.glebik.mtsproject.core.arch.util.ViewProperty

data class MainUiState(
    val lockers: ViewProperty<List<LockerUiModel>> = ViewProperty.Loading,
) : UiState

sealed interface MainIntent : UiIntent {
    data object Load : MainIntent
}

sealed interface MainEffect : UiEffect