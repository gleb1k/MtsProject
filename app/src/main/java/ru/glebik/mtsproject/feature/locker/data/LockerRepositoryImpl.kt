package ru.glebik.mtsproject.feature.locker.data

import ru.glebik.mtsproject.feature.locker.data.model.toDomain
import ru.glebik.mtsproject.feature.locker.domain.LockerRepository
import ru.glebik.mtsproject.feature.locker.domain.model.Locker
import javax.inject.Inject

class LockerRepositoryImpl @Inject constructor(
    private val api: LockerApi,
) : LockerRepository {

    override suspend fun getLockers(): Result<List<Locker>> {
        return runCatching {
            api.getLockers(
                skip = 0,
                limit = 100,
            ).map { it.toDomain() }
        }
    }

}
