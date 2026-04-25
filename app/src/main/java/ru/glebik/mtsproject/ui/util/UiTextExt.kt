package ru.glebik.mtsproject.ui.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.glebik.mtsproject.core.util.UiText


@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.ResString -> stringResource(res, *args)
    }
}


fun UiText.asString(ctx: Context): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.ResString -> ctx.getString(res, *args)
    }
}

fun String.toUiText(): UiText = UiText.DynamicString(this)

fun Int.toUiText(): UiText = UiText.ResString(this)

fun Int.toUiText(vararg args: Any): UiText = UiText.ResString(this, *args)