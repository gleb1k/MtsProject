package ru.glebik.mtsproject.feature.locker_detail

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState
import ru.glebik.mtsproject.core.arch.util.ViewProperty

data class LockerDetailUiState(
    val locker: ViewProperty<LockerDetailUiModel> = ViewProperty.Loading,
) : UiState

sealed interface LockerDetailIntent : UiIntent {
    data class Load(val lockerId: Long) : LockerDetailIntent
    data object Back : LockerDetailIntent
}

sealed interface LockerDetailEffect : UiEffect {
    data object NavigateBack : LockerDetailEffect
}
