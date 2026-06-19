package ru.glebik.mtsproject.feature.rents_api.domain.model

data class Rental(
    val id: String,
    val userId: String,
    val cellId: String,
    val pricePerHour: Int,
    val status: Status,
    val paymentStatus: PaymentStatus,
    val paymentMethodId: String?,
    val startedAt: String?,
    val endedAt: String?,
    val finalAmount: Int?,
    val openedAt: String?,
    val closedAt: String?,
    val createdAt: String,
) {
    enum class Status {
        CREATED, ACTIVE, WAITING_CLOSE, PAYMENT, COMPLETED, CANCELLED, OVERDUE
    }

    enum class PaymentStatus {
        PENDING, PAID, FAILED, DEBT
    }
}
