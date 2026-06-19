package ru.glebik.mtsproject.feature.my_rents.domain

import ru.glebik.mtsproject.core.session.UserSession
import javax.inject.Inject

interface GetMyRentsCountUseCase {
    suspend operator fun invoke(): Int
}

class GetMyRentsCountUseCaseImpl @Inject constructor(
    private val myRentsRepository: MyRentsRepository,
    private val userSession: UserSession,
) : GetMyRentsCountUseCase {
    override suspend operator fun invoke(): Int {
        val userId = userSession.getUser()?.id ?: return 0
        return myRentsRepository.getMyRents(userId).getOrThrow().size
    }
}
