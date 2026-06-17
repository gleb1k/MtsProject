package ru.glebik.mtsproject.feature.locker.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.locker.domain.model.Locker


interface GetLockersUseCase {

    suspend operator fun invoke(): Result<List<Locker>>
}

class GetLockersUseCaseImpl @Inject constructor(
    private val repository: LockerRepository,
) : GetLockersUseCase {

    override suspend fun invoke(): Result<List<Locker>> {
        return repository.getLockers()
    }
}
