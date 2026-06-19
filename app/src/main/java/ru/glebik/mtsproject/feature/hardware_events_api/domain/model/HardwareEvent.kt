package ru.glebik.mtsproject.feature.hardware_events_api.domain.model

data class HardwareEvent(
    val id: String,
    val cellId: String,
    val eventType: String,
    val processed: Boolean,
    val createdAt: String,
)

object HardwareEventType {
    const val DOOR_OPENED = "door_opened"
}
