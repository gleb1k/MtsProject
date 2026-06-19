package ru.glebik.mtsproject.feature.rents_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

interface CloseRentalUseCase {

    suspend operator fun invoke(rentalId: String): Result<Rental>
}

class CloseRentalUseCaseImpl @Inject constructor(
    private val rentsRepository: RentsRepository,
) : CloseRentalUseCase {

    override suspend fun invoke(rentalId: String): Result<Rental> {
        return rentsRepository.closeRental(rentalId)
    }
}
