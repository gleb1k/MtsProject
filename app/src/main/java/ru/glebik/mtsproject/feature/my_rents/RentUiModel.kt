package ru.glebik.mtsproject.feature.my_rents

import ru.glebik.mtsproject.core.time.DateTime
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell
import ru.glebik.mtsproject.feature.locker_detail.CellSize
import ru.glebik.mtsproject.feature.locker_detail.toCellSize
import ru.glebik.mtsproject.feature.my_rents.RentUiModel.RentStatus
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental
import ru.glebik.mtsproject.feature.rents_api.domain.model.isActiveRental

data class RentUiModel(
    val id: String,
    val cellNumber: Int,
    val cellSize: CellSize,
    val lockerAddress: String,
    val startedAt: DateTime?,
    val pricePerHour: Int,
    val status: RentStatus,
    val isActive: Boolean,
) {
    enum class RentStatus {
        ACTIVE, WAITING_CLOSE, PAYMENT, COMPLETED, CANCELLED, OVERDUE
    }
}

fun RentStatus.toBadgeText(): String {
    return when (this) {
        RentStatus.ACTIVE -> "Активно"
        RentStatus.WAITING_CLOSE -> "Ждет закрытия"
        RentStatus.PAYMENT -> "Ждет оплаты"
        RentStatus.COMPLETED -> "Завершена"
        RentStatus.CANCELLED -> "Отменена"
        RentStatus.OVERDUE -> "Просрочена"
    }
}

fun Rental.toUiModel(
    cell: LockerCell?,
    locker: Locker?,
): RentUiModel {
    val size = cell?.size?.toCellSize() ?: CellSize.Medium

    return RentUiModel(
        id = id,
        cellNumber = cell?.number ?: 0,
        cellSize = size,
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
        isActive = isActiveRental(),
    )
}
