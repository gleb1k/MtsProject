package ru.glebik.mtsproject.feature.my_rents.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.my_rents.data.MyRentsRepositoryImpl
import ru.glebik.mtsproject.feature.my_rents.data.RentalsApi
import ru.glebik.mtsproject.feature.my_rents.domain.GetMyRentsCountUseCase
import ru.glebik.mtsproject.feature.my_rents.domain.GetMyRentsCountUseCaseImpl
import ru.glebik.mtsproject.feature.my_rents.domain.GetMyRentsUseCase
import ru.glebik.mtsproject.feature.my_rents.domain.GetMyRentsUseCaseImpl
import ru.glebik.mtsproject.feature.my_rents.domain.MyRentsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class MyRentsModule {

    @Binds
    @Singleton
    abstract fun bindMyRentsRepository(
        impl: MyRentsRepositoryImpl,
    ): MyRentsRepository

    @Binds
    @Singleton
    abstract fun bindGetMyRentsUseCase(
        impl: GetMyRentsUseCaseImpl,
    ): GetMyRentsUseCase

    @Binds
    @Singleton
    abstract fun bindGetMyRentsCountUseCase(
        impl: GetMyRentsCountUseCaseImpl,
    ): GetMyRentsCountUseCase


    companion object {

        @Provides
        @Singleton
        fun provideRentalsApi(
            retrofit: Retrofit,
        ): RentalsApi {
            return retrofit.create()
        }
    }
}
