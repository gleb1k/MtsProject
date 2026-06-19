package ru.glebik.mtsproject.feature.rent_payment

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data object RentPaymentUiState : UiState

sealed interface RentPaymentIntent : UiIntent {
    data object Start : RentPaymentIntent
}

sealed interface RentPaymentEffect : UiEffect {
    data object NavigateToSuccess : RentPaymentEffect
}
