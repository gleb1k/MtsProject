package ru.glebik.mtsproject.feature.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingNavKey : NavKey

@Serializable
data object RegisterNavKey : NavKey

@Serializable
data object MainNavKey : NavKey

@Serializable
data object ProfileNavKey : NavKey

@Serializable
data class LockerDetailNavKey(
    val lockerId: Long,
) : NavKey

@Serializable
data class CellActivationNavKey(
    val lockerId: Long,
    val cellNumber: Int,
) : NavKey
