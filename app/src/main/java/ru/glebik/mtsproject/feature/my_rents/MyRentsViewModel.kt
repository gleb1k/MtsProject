package ru.glebik.mtsproject.feature.my_rents

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import ru.glebik.mtsproject.core.arch.BaseViewModel
import ru.glebik.mtsproject.core.arch.util.ViewProperty
import ru.glebik.mtsproject.core.util.UiText
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockerByIdUseCase
import ru.glebik.mtsproject.feature.locker_cell_api.domain.GetLockerCellByIdUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.GetRentsUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.model.Rental
import javax.inject.Inject

@HiltViewModel
class MyRentsViewModel @Inject constructor(
    private val getRentsUseCase: GetRentsUseCase,
    private val getLockerCellByIdUseCase: GetLockerCellByIdUseCase,
    private val getLockerByIdUseCase: GetLockerByIdUseCase,
) : BaseViewModel<MyRentsUiState, MyRentsEffect, MyRentsIntent>() {

    override fun initialState(): MyRentsUiState = MyRentsUiState()

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
                val rentals = getRentsUseCase().getOrThrow()
                val data = rentals
                    .map { rental -> rental.toUiModel() }
                    .filter { it.status == RentUiModel.RentStatus.ACTIVE }

                mutableState.value = mutableState.value.copy(
                    rents = ViewProperty.Content(data),
                )
            } catch (e: Exception) {
                Log.e("MyRentsVM", "Ошибка загрузки аренд", e)
                mutableState.update {
                    it.copy(
                        rents = ViewProperty.Error(
                            errorMessage = UiText.DynamicString("Ошибка загрузки аренд"),
                            error = e,
                        )
                    )
                }
            }
        }
    }

    private suspend fun Rental.toUiModel(): RentUiModel {
        val cell = getLockerCellByIdUseCase(cellId).getOrNull()
        val locker = cell?.let { getLockerByIdUseCase(it.stationId).getOrNull() }
        return toUiModel(cell = cell, locker = locker)
    }

    fun onNavigateBack() {
        viewModelScope.launchSafe {
            mutableEffect.emit(MyRentsEffect.NavigateBack)
        }
    }
}
