package ru.glebik.mtsproject.feature.locker_api.data.model

import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker


fun LockerResponse.toDomain(): Locker {
    return Locker(
        id = id,
        title = title,
        address = address,
        latitude = latitude,
        longitude = longitude,
        status = Locker.Status.valueOf(status),
        createdAt = createdAt,
        totalCells = totalCells,
        occupiedCells = occupiedCells,
        freeCells = freeCells,
    )
}
