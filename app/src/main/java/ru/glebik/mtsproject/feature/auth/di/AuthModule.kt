package ru.glebik.mtsproject.feature.auth.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create
import ru.glebik.mtsproject.feature.auth.data.AuthRepositoryImpl
import ru.glebik.mtsproject.feature.auth.data.api.AuthApi
import ru.glebik.mtsproject.feature.auth.domain.AuthRepository
import ru.glebik.mtsproject.feature.auth.domain.usecase.LoginUseCase
import ru.glebik.mtsproject.feature.auth.domain.usecase.LoginUseCaseImpl
import ru.glebik.mtsproject.feature.auth.domain.usecase.RegisterUseCase
import ru.glebik.mtsproject.feature.auth.domain.usecase.RegisterUseCaseImpl

/**
 * @author g.gafeev
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindRegisterUseCase(
        impl: RegisterUseCaseImpl,
    ): RegisterUseCase

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(
        impl: LoginUseCaseImpl,
    ): LoginUseCase

    companion object {

        @Provides
        @Singleton
        fun provideAuthApi(
            retrofit: Retrofit,
        ): AuthApi {
            return retrofit.create()
        }

    }
}
