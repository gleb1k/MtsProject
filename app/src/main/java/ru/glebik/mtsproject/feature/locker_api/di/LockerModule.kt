package ru.glebik.mtsproject.feature.locker_api.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.locker_api.data.LockerApi
import ru.glebik.mtsproject.feature.locker_api.data.LockerRepositoryImpl
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockersUseCase
import ru.glebik.mtsproject.feature.locker_api.domain.GetLockersUseCaseImpl
import ru.glebik.mtsproject.feature.locker_api.domain.LockerRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LockerModule {

    @Binds
    @Singleton
    abstract fun bindLockerRepository(
        impl: LockerRepositoryImpl,
    ): LockerRepository

    @Binds
    @Singleton
    abstract fun bindGetLockersUseCase(
        impl: GetLockersUseCaseImpl,
    ): GetLockersUseCase

    companion object {

        @Provides
        @Singleton
        fun provideLockerApi(
            retrofit: Retrofit,
        ): LockerApi {
            return retrofit.create()
        }
    }

}
