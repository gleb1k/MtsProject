package ru.glebik.mtsproject.feature.payment.domain.model

data class PaymentMethod(
    val id: String,
    val userId: String,
    val provider: String,
    val maskedPan: String,
    val token: String,
    val isVerified: Boolean,
    val createdAt: String,
)
