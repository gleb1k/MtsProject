package ru.glebik.mtsproject.feature.locker_cell.domain

import ru.glebik.mtsproject.feature.locker_cell.domain.model.LockerCell


interface LockerCellRepository {

    suspend fun getLockerCells(stationId: String): Result<List<LockerCell>>
}
