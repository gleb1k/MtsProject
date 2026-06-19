package ru.glebik.mtsproject.feature.rents_api.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.rents_api.data.model.CreateRentalRequest
import ru.glebik.mtsproject.feature.rents_api.data.model.RentalResponse

interface RentsApi {

    @GET("/api/v1/rentals/")
    suspend fun getRentals(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
        @Query("user_id") userId: String,
    ): List<RentalResponse>

    @POST("/api/v1/rentals/")
    suspend fun createRental(
        @Body body: CreateRentalRequest,
    ): RentalResponse

    @POST("/api/v1/rentals/{rental_id}/start")
    suspend fun startRental(
        @Path("rental_id") rentalId: String,
    ): RentalResponse
}
