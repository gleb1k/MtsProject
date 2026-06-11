package ru.glebik.mtsproject.feature.locker_detail

import androidx.compose.ui.graphics.Color
import ru.glebik.mtsproject.R
import ru.glebik.mtsproject.ui.theme.Colors

enum class CellSize(val label: String) {
    Small("Маленькая"),
    Medium("Средняя"),
    Large("Большая"),
}

data class CellUiModel(
    val number: Int,
    val pricePerHour: Int,
    val size: CellSize,
    val isOccupied: Boolean,
)

data class LockerDetailUiModel(
    val id: Long,
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

fun stubLockerDetail(lockerId: Long): LockerDetailUiModel {
    val cells = when (lockerId) {
        1L -> listOf(
            CellUiModel(number = 101, pricePerHour = 50, size = CellSize.Small, isOccupied = false),
            CellUiModel(number = 102, pricePerHour = 50, size = CellSize.Small, isOccupied = false),
            CellUiModel(number = 103, pricePerHour = 80, size = CellSize.Medium, isOccupied = false),
            CellUiModel(number = 106, pricePerHour = 150, size = CellSize.Large, isOccupied = false),
            CellUiModel(number = 104, pricePerHour = 50, size = CellSize.Small, isOccupied = true),
            CellUiModel(number = 105, pricePerHour = 150, size = CellSize.Large, isOccupied = true),
        )

        else -> listOf(
            CellUiModel(number = 201, pricePerHour = 50, size = CellSize.Small, isOccupied = false),
            CellUiModel(number = 202, pricePerHour = 80, size = CellSize.Medium, isOccupied = false),
            CellUiModel(number = 203, pricePerHour = 150, size = CellSize.Large, isOccupied = true),
        )
    }

    val lockerInfo = when (lockerId) {
        1L -> "Постомат №1" to "ул. Ленина, 45"
        2L -> "Постомат №2" to "ул. Пушкина, 228"
        else -> "Постомат №$lockerId" to "ул. Мира, 1337"
    }

    val availableCount = cells.count { !it.isOccupied }
    val occupiedCount = cells.count { it.isOccupied }

    return LockerDetailUiModel(
        id = lockerId,
        name = lockerInfo.first,
        address = lockerInfo.second,
        availableCount = availableCount,
        occupiedCount = occupiedCount,
        totalCount = cells.size,
        cells = cells,
    )
}
