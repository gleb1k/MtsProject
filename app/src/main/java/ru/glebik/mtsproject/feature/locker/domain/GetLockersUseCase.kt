package ru.glebik.mtsproject.feature.locker.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.locker.data.model.LockerResponse
import ru.glebik.mtsproject.feature.locker.data.model.toDomain
import ru.glebik.mtsproject.feature.locker.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell.domain.LockerCellRepository


interface GetLockersUseCase {

    suspend operator fun invoke(): Result<List<Locker>>
}

class GetLockersUseCaseImpl @Inject constructor(
    private val lockerRepository: LockerRepository,
    private val lockerCellRepository: LockerCellRepository,
) : GetLockersUseCase {

    override suspend fun invoke(): Result<List<Locker>> {
        return runCatching {
            val lockerResponses = lockerRepository.getLockers().getOrThrow()

            lockerResponses.map { lockerResponse ->
                val cells = lockerCellRepository.getLockerCells(lockerResponse.id).getOrDefault(emptyList())
                lockerResponse.toDomain(cells)
            }
        }
    }
}
