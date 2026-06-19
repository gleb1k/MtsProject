package ru.glebik.mtsproject.feature.auth.data

import kotlinx.serialization.json.Json
import ru.glebik.mtsproject.core.network.toApiErrorMessage
import ru.glebik.mtsproject.feature.auth.data.api.AuthApi
import ru.glebik.mtsproject.feature.auth.data.mapper.toDomain
import ru.glebik.mtsproject.feature.auth.data.model.RegisterRequest
import ru.glebik.mtsproject.feature.auth.domain.AuthRepository
import ru.glebik.mtsproject.feature.auth.domain.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val json: Json,
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
                    password = "temp123"
                )
            )

            response.toDomain()
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(Exception(it.toApiErrorMessage(json))) },
        )
    }

    override suspend fun getUsers(): Result<List<User>> {
        return runCatching {
            api.getUsers(
                skip = 0,
                limit = 100
            ).map { it.toDomain() }
        }
    }
}
