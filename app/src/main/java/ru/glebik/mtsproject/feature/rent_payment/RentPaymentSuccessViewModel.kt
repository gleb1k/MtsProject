package ru.glebik.mtsproject.feature.rent_payment

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.glebik.mtsproject.core.arch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class RentPaymentSuccessViewModel @Inject constructor() :
    BaseViewModel<RentPaymentSuccessUiState, RentPaymentSuccessEffect, RentPaymentSuccessIntent>() {

    override fun initialState(): RentPaymentSuccessUiState = RentPaymentSuccessUiState

    override fun handleIntent(intent: RentPaymentSuccessIntent) {
        when (intent) {
            RentPaymentSuccessIntent.Start -> showSuccess()
        }
    }

    private fun showSuccess() {
        viewModelScope.launchSafe {
            delay(SUCCESS_DURATION_MS)
            mutableEffect.emit(RentPaymentSuccessEffect.NavigateToMain)
        }
    }

    private companion object {
        const val SUCCESS_DURATION_MS = 2_500L
    }
}
