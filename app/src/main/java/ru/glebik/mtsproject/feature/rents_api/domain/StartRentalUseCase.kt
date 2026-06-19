package ru.glebik.mtsproject.feature.rents_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

interface StartRentalUseCase {

    suspend operator fun invoke(rentalId: String): Result<Rental>
}

class StartRentalUseCaseImpl @Inject constructor(
    private val rentsRepository: RentsRepository,
) : StartRentalUseCase {

    override suspend fun invoke(rentalId: String): Result<Rental> {
        return rentsRepository.startRental(rentalId)
    }
}
