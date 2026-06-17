package ru.glebik.mtsproject.feature.locker.data.model

import ru.glebik.mtsproject.feature.locker.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell.domain.model.LockerCell


fun LockerResponse.toDomain(cells: List<LockerCell>): Locker {
    return Locker(
        id = id,
        title = title,
        address = address,
        latitude = latitude,
        longitude = longitude,
        status = Locker.Status.valueOf(status),
        createdAt = createdAt,
        cells = cells,
    )
}
