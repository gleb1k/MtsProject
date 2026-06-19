package ru.glebik.mtsproject.feature.hardware_events_api.data

import ru.glebik.mtsproject.feature.hardware_events_api.data.model.CreateHardwareEventRequest
import ru.glebik.mtsproject.feature.hardware_events_api.data.model.toDomain
import ru.glebik.mtsproject.feature.hardware_events_api.domain.HardwareEventsRepository
import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent
import javax.inject.Inject

class HardwareEventsRepositoryImpl @Inject constructor(
    private val api: HardwareEventsApi,
) : HardwareEventsRepository {

    override suspend fun createHardwareEvent(
        cellId: String,
        eventType: String,
        processed: Boolean,
    ): Result<HardwareEvent> {
        return runCatching {
            api.createHardwareEvent(
                CreateHardwareEventRequest(
                    cellId = cellId,
                    eventType = eventType,
                    processed = processed,
                )
            ).toDomain()
        }
    }

    override suspend fun getHardwareEventById(eventId: String): Result<HardwareEvent> {
        return runCatching {
            api.getHardwareEvent(eventId).toDomain()
        }
    }

    override suspend fun getHardwareEvents(
        cellId: String?,
        processed: Boolean?,
        skip: Int,
        limit: Int,
    ): Result<List<HardwareEvent>> {
        return runCatching {
            api.getHardwareEvents(
                skip = skip,
                limit = limit,
                cellId = cellId,
                processed = processed,
            ).map { it.toDomain() }
        }
    }
}
