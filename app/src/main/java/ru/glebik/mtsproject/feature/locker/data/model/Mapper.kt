package ru.glebik.mtsproject.feature.locker.data.model

import ru.glebik.mtsproject.feature.locker.domain.model.Locker


fun LockerResponse.toDomain(): Locker {
    return Locker(
        id = id,
        title = title,
        address = address,
        latitude = latitude,
        longitude = longitude,
        status = Locker.Status.valueOf(status),
        createdAt = createdAt,
    )
}
