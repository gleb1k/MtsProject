package ru.glebik.mtsproject.feature.hardware_events_api.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.glebik.mtsproject.feature.hardware_events_api.data.model.CreateHardwareEventRequest
import ru.glebik.mtsproject.feature.hardware_events_api.data.model.HardwareEventResponse

interface HardwareEventsApi {

    @POST("/api/v1/hardware-events/")
    suspend fun createHardwareEvent(
        @Body body: CreateHardwareEventRequest,
    ): HardwareEventResponse

    @GET("/api/v1/hardware-events/{event_id}")
    suspend fun getHardwareEvent(
        @Path("event_id") eventId: String,
    ): HardwareEventResponse

    @GET("/api/v1/hardware-events/")
    suspend fun getHardwareEvents(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 100,
        @Query("cell_id") cellId: String? = null,
        @Query("processed") processed: Boolean? = null,
    ): List<HardwareEventResponse>
}
