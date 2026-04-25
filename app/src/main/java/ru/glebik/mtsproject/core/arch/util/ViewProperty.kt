package ru.glebik.mtsproject.core.arch.util

import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.util.UiText

sealed interface ViewProperty<out R> {
    data class Content<out T>(val content: T) : ViewProperty<T>
    data object Loading : ViewProperty<Nothing>
    data class Error(val errorMessage: UiText, val error: Throwable? = null) : ViewProperty<Nothing>

    val isLoading
        get() = this is Loading

    val isContent
        get() = this is Content

    val isError
        get() = this is Error
}

inline fun <R> ViewProperty<R>.requireContent(action: (R) -> Unit) {
    safeContent?.let {
        action(it)
    }
}

val <R> ViewProperty<R>.safeContent: R?
    get() = (this as? ViewProperty.Content)?.content

val <R> ViewProperty<R>.unsafeContent: R
    get() = requireNotNull((this as? ViewProperty.Content)?.content)

fun BaseViewModel<*, *, *>.loading() = ViewProperty.Loading

fun <T> BaseViewModel<*, *, *>.content(content: T) = ViewProperty.Content(content)

fun BaseViewModel<*, *, *>.failure(errorMessage: UiText, error: Throwable? = null) =
    ViewProperty.Error(errorMessage, error)
