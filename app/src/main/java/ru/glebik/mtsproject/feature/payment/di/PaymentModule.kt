package ru.glebik.mtsproject.feature.payment.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.payment.data.PaymentRepositoryImpl
import ru.glebik.mtsproject.feature.payment.data.api.PaymentApi
import ru.glebik.mtsproject.feature.payment.domain.PaymentRepository
import ru.glebik.mtsproject.feature.payment.domain.usecase.CreatePaymentMethodUseCase
import ru.glebik.mtsproject.feature.payment.domain.usecase.CreatePaymentMethodUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class PaymentModule {

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        impl: PaymentRepositoryImpl,
    ): PaymentRepository

    @Binds
    @Singleton
    abstract fun bindCreatePaymentMethodUseCase(
        impl: CreatePaymentMethodUseCaseImpl,
    ): CreatePaymentMethodUseCase

    companion object {

        @Provides
        @Singleton
        fun providePaymentApi(
            retrofit: Retrofit,
        ): PaymentApi {
            return retrofit.create()
        }
    }
}
