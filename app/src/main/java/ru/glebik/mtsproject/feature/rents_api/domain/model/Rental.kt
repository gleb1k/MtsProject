package ru.glebik.mtsproject.feature.rents_api.domain.model

import ru.glebik.mtsproject.core.time.DateTime

data class Rental(
    val id: String,
    val userId: String,
    val cellId: String,
    val pricePerHour: Int,
    val status: Status,
    val paymentStatus: PaymentStatus,
    val paymentMethodId: String?,
    val startedAt: DateTime?,
    val endedAt: DateTime?,
    val finalAmount: Int?,
    val openedAt: DateTime?,
    val closedAt: DateTime?,
    val createdAt: DateTime,
) {
    enum class Status {
        CREATED, ACTIVE, WAITING_CLOSE, PAYMENT, COMPLETED, CANCELLED, OVERDUE
    }

    enum class PaymentStatus {
        PENDING, PAID, FAILED, DEBT
    }
}
