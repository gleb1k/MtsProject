package ru.glebik.mtsproject.feature.auth.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.auth.data.model.RegisterRequest
import ru.glebik.mtsproject.feature.auth.data.model.UserResponse

interface AuthApi {

    @POST("/api/v1/users/")
    suspend fun register(
        @Body body: RegisterRequest,
    ): UserResponse

    @GET("api/v1/users/")
    suspend fun getUsers(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
    ): List<UserResponse>
}
