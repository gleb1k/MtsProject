package ru.glebik.mtsproject.feature.locker_api.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LockerResponse(
    @SerialName("id")
    val id: String,

    @SerialName("title")
    val title: String,

    @SerialName("address")
    val address: String,

    @SerialName("latitude")
    val latitude: String,

    @SerialName("longitude")
    val longitude: String,

    @SerialName("status")
    val status: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("total_cells")
    val totalCells: Int,

    @SerialName("occupied_cells")
    val occupiedCells: Int,

    @SerialName("free_cells")
    val freeCells: Int,
)
