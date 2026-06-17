package ru.glebik.mtsproject.feature.locker_cell_api.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.locker_cell_api.data.LockerCellApi
import ru.glebik.mtsproject.feature.locker_cell_api.data.LockerCellRepositoryImpl
import ru.glebik.mtsproject.feature.locker_cell_api.domain.GetLockerCellsUseCase
import ru.glebik.mtsproject.feature.locker_cell_api.domain.GetLockerCellsUseCaseImpl
import ru.glebik.mtsproject.feature.locker_cell_api.domain.LockerCellRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class LockerCellModule {

    @Binds
    @Singleton
    abstract fun bindLockerCellRepository(
        impl: LockerCellRepositoryImpl,
    ): LockerCellRepository

    @Binds
    @Singleton
    abstract fun bindGetLockerCellsUseCase(
        impl: GetLockerCellsUseCaseImpl,
    ): GetLockerCellsUseCase

    companion object {

        @Provides
        @Singleton
        fun provideLockerCellApi(
            retrofit: Retrofit,
        ): LockerCellApi {
            return retrofit.create()
        }
    }

}
