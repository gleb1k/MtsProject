package ru.glebik.mtsproject.feature.cell_activation.data

import ru.glebik.mtsproject.feature.cell_activation.CellActivationUiModel
import ru.glebik.mtsproject.feature.locker_detail.CellSize
import javax.inject.Inject

interface CellActivationRepository {
    suspend fun getCellActivation(lockerId: String, cellNumber: Int): CellActivationUiModel
}

class StubCellActivationRepository @Inject constructor() : CellActivationRepository {

    override suspend fun getCellActivation(
        lockerId: String,
        cellNumber: Int,
    ): CellActivationUiModel {
        kotlinx.coroutines.delay(300)

        return CellActivationUiModel(
            cellNumber = cellNumber,
            lockerName = "Постомат",
            lockerAddress = "Адрес постомата",
            pricePerHour = 50,
            size = CellSize.Small,
        )
    }
}
