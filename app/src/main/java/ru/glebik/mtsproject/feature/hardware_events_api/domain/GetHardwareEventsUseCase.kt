package ru.glebik.mtsproject.feature.hardware_events_api.domain

import jakarta.inject.Inject
import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent

interface GetHardwareEventsUseCase {

    suspend operator fun invoke(
        cellId: String? = null,
        processed: Boolean? = null,
        skip: Int = 0,
        limit: Int = 100,
    ): Result<List<HardwareEvent>>
}

class GetHardwareEventsUseCaseImpl @Inject constructor(
    private val repository: HardwareEventsRepository,
) : GetHardwareEventsUseCase {

    override suspend fun invoke(
        cellId: String?,
        processed: Boolean?,
        skip: Int,
        limit: Int,
    ): Result<List<HardwareEvent>> {
        return repository.getHardwareEvents(
            cellId = cellId,
            processed = processed,
            skip = skip,
            limit = limit,
        )
    }
}
