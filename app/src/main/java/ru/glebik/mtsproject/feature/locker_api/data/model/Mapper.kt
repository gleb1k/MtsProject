package ru.glebik.mtsproject.feature.locker_api.data.model

import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell


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
