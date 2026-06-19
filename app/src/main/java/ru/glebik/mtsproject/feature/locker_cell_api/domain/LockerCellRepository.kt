package ru.glebik.mtsproject.feature.locker_cell_api.domain

import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell


interface LockerCellRepository {

    suspend fun getLockerCells(stationId: String): Result<List<LockerCell>>

    suspend fun getLockerCellById(cellId: String): Result<LockerCell>
}
