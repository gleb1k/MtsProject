package ru.glebik.mtsproject.feature.locker_api.data

import ru.glebik.mtsproject.feature.locker_api.data.model.LockerResponse
import ru.glebik.mtsproject.feature.locker_api.domain.LockerRepository
import javax.inject.Inject

class LockerRepositoryImpl @Inject constructor(
    private val api: LockerApi,
) : LockerRepository {

    override suspend fun getLockers(): Result<List<LockerResponse>> {
        return runCatching {
            api.getLockers(
                skip = 0,
                limit = 100,
            )
        }
    }

    override suspend fun getLockerById(stationId: String): Result<LockerResponse> {
        return runCatching {
            api.getLockerById(stationId)
        }
    }

}
