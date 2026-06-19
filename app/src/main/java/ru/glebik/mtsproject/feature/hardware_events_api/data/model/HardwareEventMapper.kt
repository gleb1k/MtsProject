package ru.glebik.mtsproject.feature.hardware_events_api.data.model

import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent

fun HardwareEventResponse.toDomain(): HardwareEvent {
    return HardwareEvent(
        id = id,
        cellId = cellId,
        eventType = eventType,
        processed = processed,
        createdAt = createdAt,
    )
}
