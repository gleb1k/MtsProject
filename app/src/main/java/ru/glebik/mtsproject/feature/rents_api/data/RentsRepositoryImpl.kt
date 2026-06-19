package ru.glebik.mtsproject.feature.rents_api.data

import ru.glebik.mtsproject.feature.rents_api.data.model.CreateRentalRequest
import ru.glebik.mtsproject.feature.rents_api.data.model.toDomain
import ru.glebik.mtsproject.feature.rents_api.domain.RentsRepository
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental
import javax.inject.Inject

class RentsRepositoryImpl @Inject constructor(
    private val api: RentsApi,
) : RentsRepository {

    override suspend fun getRents(userId: String): Result<List<Rental>> {
        return runCatching {
            api.getRentals(
                skip = 0,
                limit = 100,
                userId = userId,
            ).map { it.toDomain() }
        }
    }

    override suspend fun createRental(
        userId: String,
        cellId: String,
        pricePerHour: Int,
        paymentMethodId: String,
    ): Result<Rental> {
        return runCatching {
            api.createRental(
                CreateRentalRequest(
                    userId = userId,
                    cellId = cellId,
                    pricePerHour = pricePerHour,
                    status = Rental.Status.CREATED.name,
                    paymentStatus = Rental.PaymentStatus.PENDING.name,
                    paymentMethodId = paymentMethodId,
                )
            ).toDomain()
        }
    }

    override suspend fun startRental(rentalId: String): Result<Rental> {
        return runCatching {
            api.startRental(rentalId).toDomain()
        }
    }
}
