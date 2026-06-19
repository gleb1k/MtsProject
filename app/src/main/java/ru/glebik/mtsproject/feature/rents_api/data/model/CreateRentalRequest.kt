package ru.glebik.mtsproject.feature.rents_api.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRentalRequest(
    @SerialName("user_id")
    val userId: String,

    @SerialName("cell_id")
    val cellId: String,

    @SerialName("price_per_hour")
    val pricePerHour: Int,

    @SerialName("status")
    val status: String,

    @SerialName("payment_status")
    val paymentStatus: String,

    @SerialName("payment_method_id")
    val paymentMethodId: String,
)
