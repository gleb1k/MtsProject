package ru.glebik.mtsproject.feature.my_rents.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RentalResponse(
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
    val paymentMethodId: String?,

    @SerialName("id")
    val id: String,

    @SerialName("started_at")
    val startedAt: String,

    @SerialName("ended_at")
    val endedAt: String?,

    @SerialName("final_amount")
    val finalAmount: Int?,

    @SerialName("opened_at")
    val openedAt: String?,

    @SerialName("closed_at")
    val closedAt: String?,

    @SerialName("created_at")
    val createdAt: String,
)
