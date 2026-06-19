package ru.glebik.mtsproject.feature.payment.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePaymentMethodRequest(
    @SerialName("user_id")
    val userId: String,

    @SerialName("provider")
    val provider: String,

    @SerialName("masked_pan")
    val maskedPan: String,

    @SerialName("token")
    val token: String,

    @SerialName("is_verified")
    val isVerified: Boolean,
)
