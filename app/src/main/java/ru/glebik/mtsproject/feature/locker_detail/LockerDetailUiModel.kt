package ru.glebik.mtsproject.feature.locker_detail

import androidx.compose.ui.graphics.Color
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.feature.locker_api.domain.model.Locker
import ru.glebik.mtsproject.feature.locker_cell_api.domain.model.LockerCell
import ru.glebik.mtsproject.ui.theme.Colors

enum class CellSize(val label: String) {
    Small("Маленькая"),
    Medium("Средняя"),
    Large("Большая"),
}

data class CellUiModel(
    val id: String,
    val number: Int,
    val pricePerHour: Int,
    val size: CellSize,
    val isOccupied: Boolean,
)

data class LockerDetailUiModel(
    val id: String,
    val name: String,
    val address: String,
    val availableCount: Int,
    val occupiedCount: Int,
    val totalCount: Int,
    val cells: List<CellUiModel>,
)

data class CellStyle(
    val iconRes: Int,
    val iconColor: Color,
    val containerColor: Color,
    val badgeColor: Color,
    val badgeTextColor: Color,
)

fun CellSize.toStyle(colors: Colors.Icon, isOccupied: Boolean): CellStyle {
    if (isOccupied) {
        return CellStyle(
            iconRes = R.drawable.lock_24,
            iconColor = colors.onGray,
            containerColor = colors.gray,
            badgeColor = colors.gray,
            badgeTextColor = colors.onGray,
        )
    }

    return when (this) {
        CellSize.Small -> CellStyle(
            iconRes = R.drawable.small_box_24,
            iconColor = colors.onBlue,
            containerColor = colors.blue,
            badgeColor = colors.blue,
            badgeTextColor = colors.onBlue,
        )

        CellSize.Medium -> CellStyle(
            iconRes = R.drawable.medium_box_24,
            iconColor = colors.onPurple,
            containerColor = colors.purple,
            badgeColor = colors.purple,
            badgeTextColor = colors.onPurple,
        )

        CellSize.Large -> CellStyle(
            iconRes = R.drawable.big_box_24,
            iconColor = colors.onOrange,
            containerColor = colors.orange,
            badgeColor = colors.orange,
            badgeTextColor = colors.onOrange,
        )
    }
}

fun Locker.toDetailUiModel(cells: List<LockerCell>): LockerDetailUiModel {
    return LockerDetailUiModel(
        id = id,
        name = title,
        address = address,
        availableCount = freeCells,
        occupiedCount = occupiedCells,
        totalCount = totalCells,
        cells = cells.map { it.toUiModel() },
    )
}

fun LockerCell.toUiModel(): CellUiModel {
    return CellUiModel(
        id = id,
        number = number,
        pricePerHour = hourlyPrice.toDoubleOrNull()?.toInt() ?: 0,
        size = size.toCellSize(),
        isOccupied = status != LockerCell.Status.AVAILABLE,
    )
}

internal fun String.toCellSize(): CellSize {
    return when (uppercase()) {
        "SMALL" -> CellSize.Small
        "MEDIUM" -> CellSize.Medium
        "LARGE" -> CellSize.Large
        else -> CellSize.Medium
    }
}
