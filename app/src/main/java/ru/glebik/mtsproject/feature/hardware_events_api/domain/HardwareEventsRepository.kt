package ru.glebik.mtsproject.feature.hardware_events_api.domain

import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent

interface HardwareEventsRepository {

    suspend fun createHardwareEvent(
        cellId: String,
        eventType: String,
        processed: Boolean = false,
    ): Result<HardwareEvent>

    suspend fun getHardwareEventById(eventId: String): Result<HardwareEvent>

    suspend fun getHardwareEvents(
        cellId: String? = null,
        processed: Boolean? = null,
        skip: Int = 0,
        limit: Int = 100,
    ): Result<List<HardwareEvent>>
}
