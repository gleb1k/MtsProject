package ru.glebik.mtsproject.feature.locker_cell.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.locker_cell.data.model.LockerCellResponse

interface LockerCellApi {

    @GET("/api/v1/locker-cells/")
    suspend fun getLockerCells(
        @Query("station_id") stationId: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
    ): List<LockerCellResponse>

}
