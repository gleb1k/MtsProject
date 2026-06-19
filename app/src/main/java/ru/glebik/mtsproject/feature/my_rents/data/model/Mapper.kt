package ru.glebik.mtsproject.feature.my_rents.data.model

import ru.glebik.mtsproject.feature.my_rents.domain.model.Rental

fun RentalResponse.toDomainModel(): Rental {
    return Rental(
        id = id,
        userId = userId,
        cellId = cellId,
        pricePerHour = pricePerHour,
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
