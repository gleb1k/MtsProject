package ru.glebik.mtsproject.feature.main

import kotlinx.collections.immutable.persistentListOf

data class LockerUiModel(
    val id: Long,
    val name: String,
    val address: String,
    val currentAvailableCells: Int,
    val maxAvailableCells: Int,
)

val stubLockers = persistentListOf<LockerUiModel>(
    LockerUiModel(
        id = 1,
        name = "Постомат №1",
        address = "ул. Ленина, 45",
        currentAvailableCells = 4,
        maxAvailableCells = 6,
    ),
    LockerUiModel(
        id = 2,
        name = "Постомат №2",
        address = "ул. Пушкина, 228",
        currentAvailableCells = 5,
        maxAvailableCells = 6,
    ),
    LockerUiModel(
        id = 1,
        name = "Постомат №1",
        address = "ул. Мира, 1337",
        currentAvailableCells = 6,
        maxAvailableCells = 6,
    ),
)