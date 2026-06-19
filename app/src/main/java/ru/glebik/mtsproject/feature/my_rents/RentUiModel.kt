package ru.glebik.mtsproject.feature.my_rents

import ru.glebik.mtsproject.feature.my_rents.RentUiModel.RentStatus
import ru.glebik.mtsproject.feature.my_rents.domain.model.Rental

data class RentUiModel(
    val id: String,
    val lockerTitle: String,
    val lockerAddress: String,
    val cellNumber: Int,
    val startTime: String,
    val endTime: String?,
    val status: RentStatus,
) {
    enum class RentStatus {
        ACTIVE, WAITING_CLOSE, PAYMENT, COMPLETED, CANCELLED, OVERDUE
    }
}


fun Rental.toUiModel(): RentUiModel {
    return RentUiModel(
        id = id,
        lockerTitle = "Ячейка #${cellId.take(4)}",
        lockerAddress = "Адрес постомата",
        cellNumber = 0,
        startTime = startedAt,
        endTime = endedAt,
        status = when (status) {
            Rental.Status.CREATED,
            Rental.Status.ACTIVE -> RentStatus.ACTIVE

            Rental.Status.WAITING_CLOSE -> RentStatus.WAITING_CLOSE
            Rental.Status.PAYMENT -> RentStatus.PAYMENT
            Rental.Status.COMPLETED -> RentStatus.COMPLETED
            Rental.Status.CANCELLED -> RentStatus.CANCELLED
            Rental.Status.OVERDUE -> RentStatus.OVERDUE
        },
    )
}
