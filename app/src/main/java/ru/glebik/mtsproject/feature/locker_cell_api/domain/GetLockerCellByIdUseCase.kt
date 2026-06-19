package ru.glebik.mtsproject.feature.locker_cell_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell

interface GetLockerCellByIdUseCase {

    suspend operator fun invoke(cellId: String): Result<LockerCell>
}

class GetLockerCellByIdUseCaseImpl @Inject constructor(
    private val repository: LockerCellRepository,
) : GetLockerCellByIdUseCase {

    override suspend fun invoke(cellId: String): Result<LockerCell> {
        return repository.getLockerCellById(cellId)
    }
}
