package ru.glebik.mtsproject.feature.locker_api.domain.model

data class Locker(
    val id: String,
    val title: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val status: Status,
    val createdAt: String,
    val totalCells: Int,
    val occupiedCells: Int,
    val freeCells: Int,
) {
    enum class Status {
        ACTIVE,
        OFFLINE,
        MAINTENANCE,
    }
}
