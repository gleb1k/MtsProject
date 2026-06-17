package ru.glebik.mtsproject.feature.locker.domain

import ru.glebik.mtsproject.feature.locker.data.model.LockerResponse


interface LockerRepository {

    suspend fun getLockers(): Result<List<LockerResponse>>
}
