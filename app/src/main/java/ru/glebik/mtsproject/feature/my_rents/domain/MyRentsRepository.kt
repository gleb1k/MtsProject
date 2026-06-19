package ru.glebik.mtsproject.feature.my_rents.domain

import ru.glebik.mtsproject.feature.my_rents.domain.model.Rental
import java.util.UUID

interface MyRentsRepository {
    suspend fun getMyRents(userId: String): Result<List<Rental>>
}
