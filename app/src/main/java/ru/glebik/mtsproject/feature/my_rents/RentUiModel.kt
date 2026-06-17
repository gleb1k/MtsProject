package ru.glebik.mtsproject.feature.my_rents

data class RentUiModel(
    val id: String,
    val lockerTitle: String,
    val lockerAddress: String,
    val cellNumber: Int,
    val startTime: String,
    val endTime: String?,
    val status: RentStatus,
)

enum class RentStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED,
}
