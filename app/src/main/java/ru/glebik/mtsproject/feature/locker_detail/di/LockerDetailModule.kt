package ru.glebik.mtsproject.feature.locker_detail.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.glebik.mtsproject.feature.locker_detail.data.LockerDetailRepository
import ru.glebik.mtsproject.feature.locker_detail.data.StubLockerDetailRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LockerDetailModule {

    @Binds
    abstract fun bindLockerDetailRepository(
        repository: StubLockerDetailRepository,
    ): LockerDetailRepository
}
