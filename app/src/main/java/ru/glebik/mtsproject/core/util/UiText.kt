package ru.glebik.mtsproject.core.util

import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class ResString(
        @param:StringRes val res: Int,
        vararg val args: Any,
    ) : UiText()

    class ResArray(
        @param:StringRes val res: Int,
        val index: Int
    )

    fun isEmpty(): Boolean {
        return when (this) {
            is DynamicString -> value.isEmpty()
            is ResString -> false
        }
    }
}
