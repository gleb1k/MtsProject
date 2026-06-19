package ru.glebik.mtsproject.feature.rents_api.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.rents_api.data.RentsApi
import ru.glebik.mtsproject.feature.rents_api.data.RentsRepositoryImpl
import ru.glebik.mtsproject.feature.rents_api.domain.CloseRentalUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.CloseRentalUseCaseImpl
import ru.glebik.mtsproject.feature.rents_api.domain.CreateRentalUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.CreateRentalUseCaseImpl
import ru.glebik.mtsproject.feature.rents_api.domain.GetRentsCountUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.GetRentsCountUseCaseImpl
import ru.glebik.mtsproject.feature.rents_api.domain.GetRentsUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.GetRentsUseCaseImpl
import ru.glebik.mtsproject.feature.rents_api.domain.RentsRepository
import ru.glebik.mtsproject.feature.rents_api.domain.StartRentalUseCase
import ru.glebik.mtsproject.feature.rents_api.domain.StartRentalUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RentsModule {

    @Binds
    @Singleton
    abstract fun bindRentsRepository(
        impl: RentsRepositoryImpl,
    ): RentsRepository

    @Binds
    @Singleton
    abstract fun bindGetRentsUseCase(
        impl: GetRentsUseCaseImpl,
    ): GetRentsUseCase

    @Binds
    @Singleton
    abstract fun bindGetRentsCountUseCase(
        impl: GetRentsCountUseCaseImpl,
    ): GetRentsCountUseCase

    @Binds
    @Singleton
    abstract fun bindCreateRentalUseCase(
        impl: CreateRentalUseCaseImpl,
    ): CreateRentalUseCase

    @Binds
    @Singleton
    abstract fun bindStartRentalUseCase(
        impl: StartRentalUseCaseImpl,
    ): StartRentalUseCase

    @Binds
    @Singleton
    abstract fun bindCloseRentalUseCase(
        impl: CloseRentalUseCaseImpl,
    ): CloseRentalUseCase

    companion object {

        @Provides
        @Singleton
        fun provideRentsApi(
            retrofit: Retrofit,
        ): RentsApi {
            return retrofit.create()
        }
    }
}
