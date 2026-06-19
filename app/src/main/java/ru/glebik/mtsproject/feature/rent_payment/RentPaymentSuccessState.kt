package ru.glebik.mtsproject.feature.rent_payment

import ru.glebik.mtsproject.core.arch.UiEffect
import ru.glebik.mtsproject.core.arch.UiIntent
import ru.glebik.mtsproject.core.arch.UiState

data object RentPaymentSuccessUiState : UiState

sealed interface RentPaymentSuccessIntent : UiIntent {
    data object Start : RentPaymentSuccessIntent
}

sealed interface RentPaymentSuccessEffect : UiEffect {
    data object NavigateToMain : RentPaymentSuccessEffect
}
