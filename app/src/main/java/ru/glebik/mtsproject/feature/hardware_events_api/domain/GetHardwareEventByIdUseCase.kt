package ru.glebik.mtsproject.feature.hardware_events_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent

interface GetHardwareEventByIdUseCase {

    suspend operator fun invoke(eventId: String): Result<HardwareEvent>
}

class GetHardwareEventByIdUseCaseImpl @Inject constructor(
    private val repository: HardwareEventsRepository,
) : GetHardwareEventByIdUseCase {

    override suspend fun invoke(eventId: String): Result<HardwareEvent> {
        return repository.getHardwareEventById(eventId)
    }
}
