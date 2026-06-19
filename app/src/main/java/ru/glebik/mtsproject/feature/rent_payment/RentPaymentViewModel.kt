package ru.glebik.mtsproject.feature.rent_payment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.glebik.mtsproject.core.arch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RentPaymentViewModel @Inject constructor() :
    BaseViewModel<RentPaymentUiState, RentPaymentEffect, RentPaymentIntent>() {

    override fun initialState(): RentPaymentUiState = RentPaymentUiState

    override fun handleIntent(intent: RentPaymentIntent) {
        when (intent) {
            RentPaymentIntent.Start -> startPayment()
        }
    }

    private fun startPayment() {
        viewModelScope.launchSafe {
            delay(PAYMENT_DURATION_MS)
            mutableEffect.emit(RentPaymentEffect.NavigateToSuccess)
        }
    }

    private companion object {
        const val PAYMENT_DURATION_MS = 1_500L
    }
}
