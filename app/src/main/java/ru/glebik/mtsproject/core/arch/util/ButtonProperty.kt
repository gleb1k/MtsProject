package ru.glebik.mtsproject.core.arch.util

sealed interface ButtonProperty {
    data object Content : ButtonProperty
    data object Loading : ButtonProperty
}

val  ButtonProperty?.toLoadingState: Boolean
    get() = when (this) {
        ButtonProperty.Content -> false
        ButtonProperty.Loading -> true
        null -> false
    }