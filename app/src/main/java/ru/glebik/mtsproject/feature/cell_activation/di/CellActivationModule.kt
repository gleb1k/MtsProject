package ru.glebik.mtsproject.feature.cell_activation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.glebik.mtsproject.feature.cell_activation.data.CellActivationRepository
import ru.glebik.mtsproject.feature.cell_activation.data.StubCellActivationRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class CellActivationModule {

    @Binds
    abstract fun bindCellActivationRepository(
        repository: StubCellActivationRepository,
    ): CellActivationRepository
}
