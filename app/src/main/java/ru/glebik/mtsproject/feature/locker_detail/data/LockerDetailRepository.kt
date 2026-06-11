package ru.glebik.mtsproject.feature.locker_detail.data

import ru.glebik.mtsproject.feature.locker_detail.LockerDetailUiModel
import ru.glebik.mtsproject.feature.locker_detail.stubLockerDetail
import javax.inject.Inject

interface LockerDetailRepository {
    suspend fun getLockerDetail(lockerId: Long): LockerDetailUiModel
}

class StubLockerDetailRepository @Inject constructor() : LockerDetailRepository {

    override suspend fun getLockerDetail(lockerId: Long): LockerDetailUiModel {
        kotlinx.coroutines.delay(300)
        return stubLockerDetail(lockerId)
    }
}
