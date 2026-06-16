package ru.glebik.mtsproject.feature.auth.data

import ru.glebik.mtsproject.feature.auth.data.api.AuthApi
import ru.glebik.mtsproject.feature.auth.data.mapper.toDomain
import ru.glebik.mtsproject.feature.auth.data.model.RegisterRequest
import ru.glebik.mtsproject.feature.auth.domain.AuthRepository
import ru.glebik.mtsproject.feature.auth.domain.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
) : AuthRepository {

    override suspend fun register(
        nickname: String,
        email: String,
        phone: String,
    ): Result<User> {
        return runCatching {
            val response = api.register(
                RegisterRequest(
                    phone = phone,
                    email = email,
                    fullName = nickname,
                    status = "active",
                )
            )

            response.toDomain()
        }
    }
}
