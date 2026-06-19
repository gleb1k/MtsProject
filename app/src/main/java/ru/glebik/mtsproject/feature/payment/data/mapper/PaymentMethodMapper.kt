package ru.glebik.mtsproject.feature.payment.data.mapper

import ru.glebik.mtsproject.feature.payment.data.model.PaymentMethodResponse
import ru.glebik.mtsproject.feature.payment.domain.model.PaymentMethod

fun PaymentMethodResponse.toDomain(): PaymentMethod {
    return PaymentMethod(
        id = id,
        userId = userId,
        provider = provider,
        maskedPan = maskedPan,
        token = token,
        isVerified = isVerified,
        createdAt = createdAt,
    )
}
