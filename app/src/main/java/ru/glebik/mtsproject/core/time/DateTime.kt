package ru.glebik.mtsproject.core.time

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

data class DateTime(
    val instant: Instant,
) {

    fun minutesUntil(other: DateTime): Long {
        return Duration.between(instant, other.instant)
            .toMinutes()
            .coerceAtLeast(0)
    }

    fun secondsUntil(other: DateTime): Long {
        return Duration.between(instant, other.instant)
            .seconds
            .coerceAtLeast(0)
    }

    companion object {

        private val formatter: DateTimeFormatter = DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .toFormatter()

        fun parse(raw: String): DateTime? {
            return runCatching {
                when {
                    raw.endsWith("Z") -> DateTime(Instant.parse(raw))

                    else -> {
                        val utcInstant = LocalDateTime.parse(raw, formatter)
                            .atZone(ZoneOffset.UTC)
                            .toInstant()

                        DateTime(utcInstant)
                    }
                }
            }.getOrNull()
        }

        fun now(): DateTime = DateTime(Instant.now())

        fun fromMillis(epochMillis: Long): DateTime {
            return DateTime(Instant.ofEpochMilli(epochMillis))
        }
    }
}

fun String?.toDateTimeOrNull(): DateTime? {
    return this?.let { DateTime.parse(it) }
}
