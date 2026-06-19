package ru.glebik.mtsproject.feature.locker_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.locker_api.data.model.toDomain
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker

interface GetLockerByIdUseCase {

    suspend operator fun invoke(stationId: String): Result<Locker>
}

class GetLockerByIdUseCaseImpl @Inject constructor(
    private val lockerRepository: LockerRepository,
) : GetLockerByIdUseCase {

    override suspend fun invoke(stationId: String): Result<Locker> {
        return runCatching {
            lockerRepository
                .getLockerById(stationId)
                .getOrThrow()
                .toDomain()
        }
    }
}
