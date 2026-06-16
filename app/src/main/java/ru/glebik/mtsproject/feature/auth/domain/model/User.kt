package ru.glebik.mtsproject.feature.auth.domain.model

data class User(
    val id: String,
    val phone: String,
    val email: String,
    val fullName: String,
    val status: String,
    val createdAt: String,
)
