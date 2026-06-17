package ru.glebik.mtsproject.feature.locker_cell.data

import ru.glebik.mtsproject.feature.locker_cell.data.model.toDomain
import ru.glebik.mtsproject.feature.locker_cell.domain.LockerCellRepository
import ru.glebik.mtsproject.feature.locker_cell.domain.model.LockerCell
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
