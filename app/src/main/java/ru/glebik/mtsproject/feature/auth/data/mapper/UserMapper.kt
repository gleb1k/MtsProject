package ru.glebik.mtsproject.feature.auth.data.mapper

import ru.glebik.mtsproject.feature.auth.data.model.UserResponse
import ru.glebik.mtsproject.feature.auth.domain.model.User


fun UserResponse.toDomain(): User {
    return User(
        id = id,
        phone = phone,
        email = email,
        fullName = fullName,
        status = status,
        createdAt = createdAt,
    )
}
