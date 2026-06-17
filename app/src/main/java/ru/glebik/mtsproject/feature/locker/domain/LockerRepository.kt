package ru.glebik.mtsproject.feature.locker.domain

import ru.glebik.mtsproject.feature.locker.domain.model.Locker


interface LockerRepository {

    suspend fun getLockers(): Result<List<Locker>>
}
