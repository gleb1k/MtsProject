package ru.glebik.mtsproject.feature.auth.data.api

import retrofit2.http.Body
import retrofit2.http.POST
import ru.glebik.mtsproject.feature.auth.data.model.RegisterRequest
import ru.glebik.mtsproject.feature.auth.data.model.UserResponse

interface AuthApi {

    @POST("/api/v1/users/")
    suspend fun register(
        @Body body: RegisterRequest,
    ): UserResponse
}
