package ru.glebik.mtsproject.feature.rent_detail

import ru.glebik.mtsproject.core.time.DateTime
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell
import ru.glebik.mtsproject.feature.locker_detail.CellSize
import ru.glebik.mtsproject.feature.locker_detail.toCellSize
import ru.glebik.mtsproject.feature.my_rents.RentUiModel
import ru.glebik.mtsproject.feature.my_rents.RentUiModel.RentStatus
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental

data class RentDetailUiModel(
    val id: String,
    val cellNumber: Int,
    val cellSize: CellSize,
    val cellSizeLabel: String,
    val lockerName: String,
    val lockerAddress: String,
    val maskedPan: String?,
    val pricePerHour: Int,
    val startedAt: DateTime?,
    val status: RentStatus,
)

fun Rental.toDetailUiModel(
    cell: LockerCell?,
    locker: Locker?,
    maskedPan: String? = null,
): RentDetailUiModel {
    val size = cell?.size?.toCellSize() ?: CellSize.Small

    return RentDetailUiModel(
        id = id,
        cellNumber = cell?.number ?: 0,
        cellSize = size,
        cellSizeLabel = size.label,
        lockerName = locker?.title ?: "—",
        lockerAddress = locker?.address ?: "—",
        maskedPan = maskedPan,
        pricePerHour = pricePerHour,
        startedAt = startedAt ?: createdAt,
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

fun formatCardDisplay(maskedPan: String?): String {
    val lastFour = maskedPan
        ?.filter { it.isDigit() }
        ?.takeLast(4)
        .orEmpty()

    return if (lastFour.length == 4) {
        "Карта •••• $lastFour"
    } else {
        "Карта •••• ••••"
    }
}
