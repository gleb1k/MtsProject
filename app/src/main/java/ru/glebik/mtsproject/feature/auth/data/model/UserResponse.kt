package ru.glebik.mtsproject.feature.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("phone")
    val phone: String,

    @SerialName("email")
    val email: String,

    @SerialName("full_name")
    val fullName: String,

    @SerialName("status")
    val status: String,

    @SerialName("id")
    val id: String,

    @SerialName("created_at")
    val createdAt: String,
)
