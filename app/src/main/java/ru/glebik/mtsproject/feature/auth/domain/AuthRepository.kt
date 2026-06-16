package ru.glebik.mtsproject.feature.auth.domain

import ru.glebik.mtsproject.feature.auth.domain.model.User


interface AuthRepository {

    suspend fun register(
        nickname: String,
        email: String,
        phone: String,
    ): Result<User>
}
