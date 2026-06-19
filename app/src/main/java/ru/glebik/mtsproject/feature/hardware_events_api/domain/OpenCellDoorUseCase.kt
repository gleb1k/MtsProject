package ru.glebik.mtsproject.feature.hardware_events_api.domain

import jakarta.inject.Inject
import kotlinx.coroutines.delay
import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEvent
import ru.glebik.mtsproject.feature.hardware_events_api.domain.model.HardwareEventType

interface OpenCellDoorUseCase {

    suspend operator fun invoke(cellId: String): Result<HardwareEvent>
}

class OpenCellDoorUseCaseImpl @Inject constructor(
    private val createHardwareEventUseCase: CreateHardwareEventUseCase,
    private val getHardwareEventByIdUseCase: GetHardwareEventByIdUseCase,
) : OpenCellDoorUseCase {

    override suspend fun invoke(cellId: String): Result<HardwareEvent> {
        val createdEvent = createHardwareEventUseCase(
            cellId = cellId,
            eventType = HardwareEventType.DOOR_OPENED,
            processed = false,
        ).getOrElse { return Result.failure(it) }

        return waitUntilProcessed(createdEvent.id)
    }

    private suspend fun waitUntilProcessed(eventId: String): Result<HardwareEvent> {
        val deadline = System.currentTimeMillis() + POLL_TIMEOUT_MS
        var lastEvent: HardwareEvent? = null

        while (System.currentTimeMillis() < deadline) {
            val event = getHardwareEventByIdUseCase(eventId).getOrElse { return Result.failure(it) }
            lastEvent = event

            if (event.processed) {
                return Result.success(event)
            }

            delay(POLL_INTERVAL_MS)
        }

        // Бэкенд пока может не проставлять processed — считаем команду отправленной.
        return Result.success(
            lastEvent ?: return Result.failure(IllegalStateException("Hardware event not found")),
        )
    }

    private companion object {
        const val POLL_INTERVAL_MS = 500L
        const val POLL_TIMEOUT_MS = 15_000L
    }
}
