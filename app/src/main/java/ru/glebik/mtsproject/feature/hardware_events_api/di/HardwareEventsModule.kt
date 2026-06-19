package ru.glebik.mtsproject.feature.hardware_events_api.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.hardware_events_api.data.HardwareEventsApi
import ru.glebik.mtsproject.feature.hardware_events_api.data.HardwareEventsRepositoryImpl
import ru.glebik.mtsproject.feature.hardware_events_api.domain.CreateHardwareEventUseCase
import ru.glebik.mtsproject.feature.hardware_events_api.domain.CreateHardwareEventUseCaseImpl
import ru.glebik.mtsproject.feature.hardware_events_api.domain.GetHardwareEventByIdUseCase
import ru.glebik.mtsproject.feature.hardware_events_api.domain.GetHardwareEventByIdUseCaseImpl
import ru.glebik.mtsproject.feature.hardware_events_api.domain.GetHardwareEventsUseCase
import ru.glebik.mtsproject.feature.hardware_events_api.domain.GetHardwareEventsUseCaseImpl
import ru.glebik.mtsproject.feature.hardware_events_api.domain.HardwareEventsRepository
import ru.glebik.mtsproject.feature.hardware_events_api.domain.OpenCellDoorUseCase
import ru.glebik.mtsproject.feature.hardware_events_api.domain.OpenCellDoorUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class HardwareEventsModule {

    @Binds
    @Singleton
    abstract fun bindHardwareEventsRepository(
        impl: HardwareEventsRepositoryImpl,
    ): HardwareEventsRepository

    @Binds
    @Singleton
    abstract fun bindCreateHardwareEventUseCase(
        impl: CreateHardwareEventUseCaseImpl,
    ): CreateHardwareEventUseCase

    @Binds
    @Singleton
    abstract fun bindGetHardwareEventByIdUseCase(
        impl: GetHardwareEventByIdUseCaseImpl,
    ): GetHardwareEventByIdUseCase

    @Binds
    @Singleton
    abstract fun bindGetHardwareEventsUseCase(
        impl: GetHardwareEventsUseCaseImpl,
    ): GetHardwareEventsUseCase

    @Binds
    @Singleton
    abstract fun bindOpenCellDoorUseCase(
        impl: OpenCellDoorUseCaseImpl,
    ): OpenCellDoorUseCase

    companion object {

        @Provides
        @Singleton
        fun provideHardwareEventsApi(
            retrofit: Retrofit,
        ): HardwareEventsApi {
            return retrofit.create()
        }
    }
}
