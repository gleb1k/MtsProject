package ru.glebik.mtsproject.feature.rents_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

interface GetRentsUseCase {

    suspend operator fun invoke(): Result<List<Rental>>
}

class GetRentsUseCaseImpl @Inject constructor(
    private val rentsRepository: RentsRepository,
    private val userSession: UserSession,
) : GetRentsUseCase {

    override suspend fun invoke(): Result<List<Rental>> {
        val userId = userSession.getUser()?.id
            ?: return Result.failure(IllegalStateException("User not authenticated"))

        return rentsRepository.getRents(userId)
    }
}
