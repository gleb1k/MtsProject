package ru.glebik.mtsproject.feature.payment.domain.usecase

import jakarta.inject.Inject
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.payment.domain.PaymentRepository
import ru.glebik.mtsproject.feature.payment.domain.model.PaymentMethod

interface CreatePaymentMethodUseCase {

    suspend operator fun invoke(
        provider: String,
        maskedPan: String,
        token: String,
        isVerified: Boolean = false,
    ): Result<PaymentMethod>
}

class CreatePaymentMethodUseCaseImpl @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val userSession: UserSession,
) : CreatePaymentMethodUseCase {

    override suspend fun invoke(
        provider: String,
        maskedPan: String,
        token: String,
        isVerified: Boolean,
    ): Result<PaymentMethod> {
        val userId = userSession.getUser()?.id
            ?: return Result.failure(IllegalStateException("User not authenticated"))

        return paymentRepository.createPaymentMethod(
            userId = userId,
            provider = provider,
            maskedPan = maskedPan,
            token = token,
            isVerified = isVerified,
        )
    }
}
