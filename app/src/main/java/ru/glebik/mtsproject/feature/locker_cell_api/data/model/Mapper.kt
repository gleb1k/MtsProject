package ru.glebik.mtsproject.feature.locker_cell_api.data.model

import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell


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
