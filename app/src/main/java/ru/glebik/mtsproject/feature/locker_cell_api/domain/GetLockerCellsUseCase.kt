package ru.glebik.mtsproject.feature.locker_cell_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell


interface GetLockerCellsUseCase {

    suspend operator fun invoke(stationId: String): Result<List<LockerCell>>
}

class GetLockerCellsUseCaseImpl @Inject constructor(
    private val repository: LockerCellRepository,
) : GetLockerCellsUseCase {

    override suspend fun invoke(stationId: String): Result<List<LockerCell>> {
        return repository.getLockerCells(stationId)
    }
}
