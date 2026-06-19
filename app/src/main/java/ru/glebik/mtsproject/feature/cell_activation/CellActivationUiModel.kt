package ru.glebik.mtsproject.feature.cell_activation

import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell
import ru.glebik.mtsproject.feature.locker_detail.CellSize
import ru.glebik.mtsproject.feature.locker_detail.toCellSize

data class CellActivationUiModel(
    val cellNumber: Int,
    val lockerName: String,
    val lockerAddress: String,
    val pricePerHour: Int,
    val size: CellSize,
)

fun LockerCell.toActivationUiModel(locker: Locker): CellActivationUiModel {
    return CellActivationUiModel(
        cellNumber = number,
        lockerName = locker.title,
        lockerAddress = locker.address,
        pricePerHour = hourlyPrice.toDoubleOrNull()?.toInt() ?: 0,
        size = size.toCellSize(),
    )
}
