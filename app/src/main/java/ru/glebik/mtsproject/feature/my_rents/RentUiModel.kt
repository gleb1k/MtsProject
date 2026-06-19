package ru.glebik.mtsproject.feature.my_rents

import ru.glebik.mtsproject.core.time.DateTime
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell
import ru.glebik.mtsproject.feature.locker_detail.toCellSize
import ru.glebik.mtsproject.feature.my_rents.RentUiModel.RentStatus
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

data class RentUiModel(
    val id: String,
    val cellNumber: Int,
    val cellSizeLabel: String,
    val lockerAddress: String,
    val startedAt: DateTime?,
    val pricePerHour: Int,
    val status: RentStatus,
) {
    enum class RentStatus {
        ACTIVE, WAITING_CLOSE, PAYMENT, COMPLETED, CANCELLED, OVERDUE
    }
}

fun Rental.toUiModel(
    cell: LockerCell?,
    locker: Locker?,
): RentUiModel {
    return RentUiModel(
        id = id,
        cellNumber = cell?.number ?: 0,
        cellSizeLabel = cell?.size?.toCellSize()?.label ?: "—",
        lockerAddress = locker?.address ?: "—",
        startedAt = startedAt ?: createdAt,
        pricePerHour = pricePerHour,
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
