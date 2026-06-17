package ru.glebik.mtsproject.feature.locker_cell_api.domain.model

data class LockerCell(
    val id: String,
    val stationId: String,
    val number: Int,
    val size: String,
    val hourlyPrice: String,
    val status: Status,
    val hardwareId: String,
    val createdAt: String,
) {
    enum class Status {
        AVAILABLE,
        OCCUPIED,
        MAINTENANCE,
    }
}
