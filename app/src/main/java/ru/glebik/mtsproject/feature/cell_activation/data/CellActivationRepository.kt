package ru.glebik.mtsproject.feature.cell_activation.data

import ru.glebik.mtsproject.feature.cell_activation.CellActivationUiModel
import ru.glebik.mtsproject.feature.locker_detail.stubLockerDetail
import javax.inject.Inject

interface CellActivationRepository {
    suspend fun getCellActivation(lockerId: Long, cellNumber: Int): CellActivationUiModel
}

class StubCellActivationRepository @Inject constructor() : CellActivationRepository {

    override suspend fun getCellActivation(
        lockerId: Long,
        cellNumber: Int,
    ): CellActivationUiModel {
        kotlinx.coroutines.delay(300)

        val locker = stubLockerDetail(lockerId)
        val cell = locker.cells.first { it.number == cellNumber && !it.isOccupied }

        return CellActivationUiModel(
            cellNumber = cell.number,
            lockerName = locker.name,
            lockerAddress = locker.address,
            pricePerHour = cell.pricePerHour,
            size = cell.size,
        )
    }
}
