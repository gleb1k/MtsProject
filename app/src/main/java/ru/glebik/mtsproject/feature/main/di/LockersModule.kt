package ru.glebik.mtsproject.feature.main.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.glebik.mtsproject.feature.main.data.LockersRepository
import ru.glebik.mtsproject.feature.main.data.StubLockersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LockersModule {

    @Binds
    @Singleton
    abstract fun bindLockersRepository(
        impl: StubLockersRepository
    ): LockersRepository
}