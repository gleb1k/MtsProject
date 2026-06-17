package ru.glebik.mtsproject.feature.locker_api.domain.model

import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell

data class Locker(
    val id: String,
    val title: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val status: Status,
    val createdAt: String,
    val cells: List<LockerCell>,
) {
    enum class Status {
        ACTIVE,
        OFFLINE,
        MAINTENANCE,
    }
}
