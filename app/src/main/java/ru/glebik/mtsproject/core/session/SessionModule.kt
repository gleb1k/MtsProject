package ru.glebik.mtsproject.core.session

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionModule {

    @Provides
    @Singleton
    fun provideUserSession(
        @ApplicationContext context: Context,
    ): UserSession {
        return UserSession(context)
    }
}
