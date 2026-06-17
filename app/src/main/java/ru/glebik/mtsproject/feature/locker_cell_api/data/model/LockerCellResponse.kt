package ru.glebik.mtsproject.feature.locker_cell_api.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LockerCellResponse(
    @SerialName("station_id")
    val stationId: String,

    @SerialName("number")
    val number: Int,

    @SerialName("size")
    val size: String,

    @SerialName("hourly_price")
    val hourlyPrice: String,

    @SerialName("status")
    val status: String,

    @SerialName("hardware_id")
    val hardwareId: String,

    @SerialName("id")
    val id: String,

    @SerialName("created_at")
    val createdAt: String,
)
