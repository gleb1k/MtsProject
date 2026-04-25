package ru.glebik.mtsproject.core.arch

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S, E, I> :
    ViewModel() where S : UiState, E : UiEffect, I : UiIntent {

    protected val mutableState by lazy { MutableStateFlow(initialState()) }
    val state: StateFlow<S>
        get() = mutableState.asStateFlow()

    protected val mutableEffect by lazy { MutableSharedFlow<E>() }
    val effect: SharedFlow<E>
        get() = mutableEffect.asSharedFlow()

    private val handler = CoroutineExceptionHandler { _, throwable ->
        onException(throwable)
    }

    protected fun CoroutineScope.launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit,
    ): Job = launch(context + handler, block = block)

    protected fun <T> CoroutineScope.asyncSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> T,
    ): Deferred<T> = async(context + handler, block = block)

    protected abstract fun initialState(): S

    abstract fun handleIntent(intent: I)

    protected open fun onException(error: Throwable) {
        Log.e(TAG, "Exception handled", error)
    }

    private companion object {
        const val TAG = "ViewModelTag"
    }
}
