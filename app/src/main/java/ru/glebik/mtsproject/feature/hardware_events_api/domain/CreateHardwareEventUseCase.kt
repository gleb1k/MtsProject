package ru.glebik.mtsproject.feature.hardware_events_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent

interface CreateHardwareEventUseCase {

    suspend operator fun invoke(
        cellId: String,
        eventType: String,
        processed: Boolean = false,
    ): Result<HardwareEvent>
}

class CreateHardwareEventUseCaseImpl @Inject constructor(
    private val repository: HardwareEventsRepository,
) : CreateHardwareEventUseCase {

    override suspend fun invoke(
        cellId: String,
        eventType: String,
        processed: Boolean,
    ): Result<HardwareEvent> {
        return repository.createHardwareEvent(
            cellId = cellId,
            eventType = eventType,
            processed = processed,
        )
    }
}
