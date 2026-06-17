package ru.glebik.mtsproject.feature.locker_cell_api.data

import ru.glebik.mtsproject.feature.locker_cell_api.data.model.toDomain
import ru.glebik.mtsproject.feature.locker_cell_api.domain.LockerCellRepository
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell
import javax.inject.Inject

class LockerCellRepositoryImpl @Inject constructor(
    private val api: LockerCellApi,
) : LockerCellRepository {

    override suspend fun getLockerCells(stationId: String): Result<List<LockerCell>> {
        return runCatching {
            api.getLockerCells(
                stationId = stationId,
                skip = 0,
                limit = 100,
            ).map { it.toDomain() }
        }
    }

}
