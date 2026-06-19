package ru.glebik.mtsproject.feature.hardware_events_api.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HardwareEventResponse(
    @SerialName("id")
    val id: String,

    @SerialName("cell_id")
    val cellId: String,

    @SerialName("event_type")
    val eventType: String,

    @SerialName("raw_payload")
    val rawPayload: Map<String, String>? = emptyMap(),

    @SerialName("processed")
    val processed: Boolean,

    @SerialName("created_at")
    val createdAt: String,
)
