package ru.glebik.mtsproject.feature.rents_api.data.model

import ru.glebik.mtsproject.core.time.toDateTimeOrNull
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

fun RentalResponse.toDomain(): Rental {
    return Rental(
        id = id,
        userId = userId,
        cellId = cellId,
        pricePerHour = pricePerHour.toInt(),
        status = Rental.Status.valueOf(status),
        paymentStatus = Rental.PaymentStatus.valueOf(paymentStatus),
        paymentMethodId = paymentMethodId,
        startedAt = startedAt.toDateTimeOrNull(),
        endedAt = endedAt.toDateTimeOrNull(),
        finalAmount = finalAmount,
        openedAt = openedAt.toDateTimeOrNull(),
        closedAt = closedAt.toDateTimeOrNull(),
        createdAt = requireNotNull(createdAt.toDateTimeOrNull()) {
            "Invalid createdAt: $createdAt"
        },
    )
}
