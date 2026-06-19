package ru.glebik.mtsproject.feature.my_rents

import ru.glebik.mtsproject.core.time.DateTime
import kotlin.math.roundToInt

fun formatRentalDuration(
    startedAt: DateTime?,
    now: DateTime = DateTime.now(),
): String {
    if (startedAt == null) return "—"

    return formatDurationSeconds(startedAt.secondsUntil(now))
}

fun calculateCurrentCost(
    pricePerHour: Int,
    startedAt: DateTime?,
    now: DateTime = DateTime.now(),
): Int {
    if (startedAt == null) return 0

    val totalSeconds = startedAt.secondsUntil(now)
    return ((totalSeconds / 3600.0) * pricePerHour).roundToInt()
}

fun formatRentalDurationCompact(
    startedAt: DateTime?,
    now: DateTime = DateTime.now(),
): String {
    if (startedAt == null) return "—"

    val totalSeconds = startedAt.secondsUntil(now)
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return buildList {
        if (hours > 0) add("${hours}ч")
        if (minutes > 0) add("${minutes}мин")
        add("${seconds}с")
    }.joinToString(" ")
}

private fun formatDurationSeconds(totalSeconds: Long): String {
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return buildList {
        if (hours > 0) add("${hours}ч")
        if (minutes > 0) add("${minutes}мин")
        if (seconds > 0 || isEmpty()) add("${seconds}с")
    }.joinToString(" ")
}
