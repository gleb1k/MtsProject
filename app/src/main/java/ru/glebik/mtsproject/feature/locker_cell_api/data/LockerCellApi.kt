package ru.glebik.mtsproject.feature.locker_cell_api.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.locker_cell_api.data.model.LockerCellResponse

interface LockerCellApi {

    @GET("/api/v1/locker-cells/")
    suspend fun getLockerCells(
        @Query("station_id") stationId: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
    ): List<LockerCellResponse>

    @GET("/api/v1/locker-cells/{cell_id}")
    suspend fun getLockerCellById(
        @Path("cell_id") cellId: String,
    ): LockerCellResponse

}
