package ru.glebik.mtsproject.feature.my_rents.data

import ru.glebik.mtsproject.feature.my_rents.data.model.toDomainModel
import ru.glebik.mtsproject.feature.my_rents.domain.MyRentsRepository
import ru.glebik.mtsproject.feature.my_rents.domain.model.Rental
import javax.inject.Inject

class MyRentsRepositoryImpl @Inject constructor(
    private val api: RentalsApi,
) : MyRentsRepository {

    override suspend fun getMyRents(userId: String): Result<List<Rental>> {
        return runCatching {
            val responseList = api.getRentals(
                skip = 0,
                limit = 100,
                userId = userId,
            )
            responseList.map { it.toDomainModel() }
        }
    }
}
