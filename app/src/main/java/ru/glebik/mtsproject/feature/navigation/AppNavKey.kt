package ru.glebik.mtsproject.feature.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingNavKey : NavKey

@Serializable
data object RegisterNavKey : NavKey

@Serializable
data object LoginNavKey : NavKey

@Serializable
data object MainNavKey : NavKey

@Serializable
data object ProfileNavKey : NavKey

@Serializable
data class LockerDetailNavKey(
    val lockerId: String,
) : NavKey

@Serializable
data class CellActivationNavKey(
    val cellId: String,
) : NavKey

@Serializable
data object MyRentsNavKey : NavKey

@Serializable
data class RentDetailNavKey(
    val rentalId: String,
) : NavKey
