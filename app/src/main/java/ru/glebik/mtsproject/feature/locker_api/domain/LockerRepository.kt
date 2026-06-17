package ru.glebik.mtsproject.feature.locker_api.domain

import ru.glebik.mtsproject.feature.locker_api.data.model.LockerResponse


interface LockerRepository {

    suspend fun getLockers(): Result<List<LockerResponse>>
}
