package ru.glebik.mtsproject.feature.locker_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.locker_api.data.model.toDomain
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.LockerCellRepository


interface GetLockersUseCase {

    suspend operator fun invoke(): Result<List<Locker>>
}

class GetLockersUseCaseImpl @Inject constructor(
    private val lockerRepository: LockerRepository,
) : GetLockersUseCase {

    override suspend fun invoke(): Result<List<Locker>> {
        return runCatching {
            val lockerResponses = lockerRepository.getLockers().getOrThrow()

            lockerResponses.map { lockerResponse ->
                lockerResponse.toDomain()
            }
        }
    }
}
