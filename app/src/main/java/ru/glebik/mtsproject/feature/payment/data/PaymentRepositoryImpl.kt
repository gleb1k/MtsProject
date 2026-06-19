package ru.glebik.mtsproject.feature.payment.data

import ru.glebik.mtsproject.feature.payment.data.api.PaymentApi
import ru.glebik.mtsproject.feature.payment.data.mapper.toDomain
import ru.glebik.mtsproject.feature.payment.data.model.CreatePaymentMethodRequest
import ru.glebik.mtsproject.feature.payment.domain.PaymentRepository
import ru.glebik.mtsproject.feature.payment.domain.model.PaymentMethod
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val api: PaymentApi,
) : PaymentRepository {

    override suspend fun createPaymentMethod(
        userId: String,
        provider: String,
        maskedPan: String,
        token: String,
        isVerified: Boolean,
    ): Result<PaymentMethod> {
        return runCatching {
            api.createPaymentMethod(
                CreatePaymentMethodRequest(
                    userId = userId,
                    provider = provider,
                    maskedPan = maskedPan,
                    token = token,
                    isVerified = isVerified,
                )
            ).toDomain()
        }
    }

    override suspend fun getPaymentMethodById(methodId: String): Result<PaymentMethod> {
        return runCatching {
            api.getPaymentMethod(methodId).toDomain()
        }
    }
}
