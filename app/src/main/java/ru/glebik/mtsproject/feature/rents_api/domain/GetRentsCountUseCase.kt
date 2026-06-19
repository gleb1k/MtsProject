package ru.glebik.mtsproject.feature.rents_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.rents_api.domain.model.isActiveRental

interface GetRentsCountUseCase {

    suspend operator fun invoke(): Int
}

class GetRentsCountUseCaseImpl @Inject constructor(
    private val rentsRepository: RentsRepository,
    private val userSession: UserSession,
) : GetRentsCountUseCase {

    override suspend fun invoke(): Int {
        val userId = userSession.getUser()?.id ?: return 0
        return rentsRepository.getRents(userId)
            .getOrThrow()
            .count { it.isActiveRental() }
    }
}
