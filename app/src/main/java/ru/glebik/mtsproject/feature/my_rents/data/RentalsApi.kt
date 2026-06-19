package ru.glebik.mtsproject.feature.my_rents.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.my_rents.data.model.RentalResponse

interface RentalsApi {

    @GET("/api/v1/rentals/")
    suspend fun getRentals(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
        @Query("user_id") userId: String,
    ): List<RentalResponse>
}
