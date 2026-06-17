package ru.glebik.mtsproject.feature.my_rents

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import javax.inject.Inject

@HiltViewModel
class MyRentsViewModel @Inject constructor(
    // TODO: добавить репозиторий для загрузки аренд
) : BaseViewModel<MyRentsUiState, MyRentsEffect, MyRentsIntent>() {

    override fun initialState(): MyRentsUiState {
        return MyRentsUiState()
    }

    init {
        handleIntent(MyRentsIntent.Load)
    }

    override fun handleIntent(intent: MyRentsIntent) {
        when (intent) {
            MyRentsIntent.Load -> loadRents()
        }
    }

    private fun loadRents() {
        viewModelScope.launchSafe {
            mutableState.update {
                it.copy(rents = ViewProperty.Loading)
            }

            try {
                // TODO: загрузить аренды из репозитория
                val data = emptyList<RentUiModel>()

                mutableState.value = mutableState.value.copy(
                    rents = ViewProperty.Content(data)
                )

            } catch (e: Exception) {
                Log.e("MyRentsVM", "Ошибка загрузки аренд", e)
                mutableState.update {
                    it.copy(
                        rents = ViewProperty.Error(
                            errorMessage = UiText.DynamicString("Ошибка загрузки аренд"),
                            error = e
                        )
                    )
                }
            }
        }
    }

    fun onNavigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(MyRentsEffect.NavigateBack)
        }
    }
}
