package ru.glebik.mtsproject.feature.my_rents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun rememberRentNowMillis(
    updateIntervalMillis: Long = 1_000L,
): Long {
    var nowMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }

    LaunchedEffect(updateIntervalMillis) {
        while (true) {
            nowMillis = System.currentTimeMillis()
            delay(updateIntervalMillis)
        }
    }

    return nowMillis
}
