package ru.glebik.mtsproject.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

data class AppShapes(
    val default: RoundedCornerShape,
)

internal val appShapes = AppShapes(
    default = RoundedCornerShape(16.dp),
)
