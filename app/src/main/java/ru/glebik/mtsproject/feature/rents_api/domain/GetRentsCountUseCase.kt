package ru.glebik.mtsproject.feature.rents_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.core.session.UserSession

interface GetRentsCountUseCase {

    suspend operator fun invoke(): Int
}

class GetRentsCountUseCaseImpl @Inject constructor(
    private val rentsRepository: RentsRepository,
    private val userSession: UserSession,
) : GetRentsCountUseCase {

    override suspend fun invoke(): Int {
        val userId = userSession.getUser()?.id ?: return 0
        return rentsRepository.getRents(userId).getOrThrow().size
    }
}
