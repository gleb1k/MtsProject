package ru.glebik.mtsproject.feature.my_rents.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MyRentsModule {

    // TODO: предоставить репозиторий для загрузки аренд
}
