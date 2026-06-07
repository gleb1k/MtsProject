package ru.glebik.mtsproject.feature.main.data

import ru.glebik.mtsproject.feature.main.LockerUiModel
import ru.glebik.mtsproject.feature.main.stubLockers
import javax.inject.Inject

interface LockersRepository {
    suspend fun getLockers(): List<LockerUiModel>
}

class StubLockersRepository @Inject constructor() : LockersRepository {

    override suspend fun getLockers(): List<LockerUiModel> {
        // имитация задержки сети
        kotlinx.coroutines.delay(500)

        return stubLockers
    }
}