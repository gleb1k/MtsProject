package ru.glebik.mtsproject.feature.rents_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

interface CreateRentalUseCase {

    suspend operator fun invoke(
        cellId: String,
        pricePerHour: Int,
        paymentMethodId: String,
    ): Result<Rental>
}

class CreateRentalUseCaseImpl @Inject constructor(
    private val rentsRepository: RentsRepository,
    private val userSession: UserSession,
) : CreateRentalUseCase {

    override suspend fun invoke(
        cellId: String,
        pricePerHour: Int,
        paymentMethodId: String,
    ): Result<Rental> {
        val userId = userSession.getUser()?.id
            ?: return Result.failure(IllegalStateException("User not authenticated"))

        return rentsRepository.createRental(
            userId = userId,
            cellId = cellId,
            pricePerHour = pricePerHour,
            paymentMethodId = paymentMethodId,
        )
    }
}
