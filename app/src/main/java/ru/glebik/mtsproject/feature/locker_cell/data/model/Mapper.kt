package ru.glebik.mtsproject.feature.locker_cell.data.model

import ru.glebik.mtsproject.feature.locker_cell.domain.model.LockerCell


fun LockerCellResponse.toDomain(): LockerCell {
    return LockerCell(
        id = id,
        stationId = stationId,
        number = number,
        size = size,
        hourlyPrice = hourlyPrice,
        status = LockerCell.Status.valueOf(status),
        hardwareId = hardwareId,
        createdAt = createdAt,
    )
}
