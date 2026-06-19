package ru.glebik.mtsproject.feature.main

data class LockerUiModel(
    val id: String,
    val name: String,
    val address: String,
    val currentAvailableCells: Int,
    val maxAvailableCells: Int,
)
