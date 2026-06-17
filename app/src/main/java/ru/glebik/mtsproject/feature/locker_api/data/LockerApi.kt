package ru.glebik.mtsproject.feature.locker_api.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.locker_api.data.model.LockerResponse

interface LockerApi {

    @GET("/api/v1/locker-stations/")
    suspend fun getLockers(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
    ): List<LockerResponse>

}
