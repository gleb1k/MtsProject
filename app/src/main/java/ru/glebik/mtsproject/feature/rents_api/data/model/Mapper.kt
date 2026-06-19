package ru.glebik.mtsproject.feature.rents_api.data.model

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
        startedAt = startedAt,
        endedAt = endedAt,
        finalAmount = finalAmount,
        openedAt = openedAt,
        closedAt = closedAt,
        createdAt = createdAt,
    )
}
