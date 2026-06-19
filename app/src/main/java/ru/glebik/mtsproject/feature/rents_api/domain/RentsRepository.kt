package ru.glebik.mtsproject.feature.rents_api.domain

import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

interface RentsRepository {

    suspend fun getRents(userId: String): Result<List<Rental>>

    suspend fun createRental(
        userId: String,
        cellId: String,
        pricePerHour: Int,
        paymentMethodId: String,
    ): Result<Rental>

    suspend fun startRental(rentalId: String): Result<Rental>
}
