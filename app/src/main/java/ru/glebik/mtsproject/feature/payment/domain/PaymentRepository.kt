package ru.glebik.mtsproject.feature.payment.domain

import ru.glebik.mtsproject.feature.payment.domain.model.PaymentMethod

interface PaymentRepository {

    suspend fun createPaymentMethod(
        userId: String,
        provider: String,
        maskedPan: String,
        token: String,
        isVerified: Boolean,
    ): Result<PaymentMethod>

    suspend fun getPaymentMethodById(methodId: String): Result<PaymentMethod>
}
