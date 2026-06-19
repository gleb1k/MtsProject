package ru.glebik.mtsproject.feature.my_rents.domain

import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.my_rents.domain.model.Rental
import javax.inject.Inject

interface GetMyRentsUseCase {
    suspend operator fun invoke(): Result<List<Rental>>
}

class GetMyRentsUseCaseImpl @Inject constructor(
    private val myRentsRepository: MyRentsRepository,
    private val userSession: UserSession,
) : GetMyRentsUseCase {
    override suspend operator fun invoke(): Result<List<Rental>> {
        val userId = userSession.getUser()?.id
            ?: return Result.failure(Exception("User not authenticated"))
        return myRentsRepository.getMyRents(userId)
    }
}
