package ru.glebik.mtsproject.feature.payment.domain.usecase

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.payment.domain.PaymentRepository
import ru.glebik.mtsproject.feature.payment.domain.model.PaymentMethod

interface GetPaymentMethodByIdUseCase {

    suspend operator fun invoke(methodId: String): Result<PaymentMethod>
}

class GetPaymentMethodByIdUseCaseImpl @Inject constructor(
    private val paymentRepository: PaymentRepository,
) : GetPaymentMethodByIdUseCase {

    override suspend fun invoke(methodId: String): Result<PaymentMethod> {
        return paymentRepository.getPaymentMethodById(methodId)
    }
}
