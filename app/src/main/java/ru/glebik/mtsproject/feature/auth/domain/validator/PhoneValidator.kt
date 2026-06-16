package ru.glebik.mtsproject.feature.auth.domain.validator

object PhoneValidator {

    private val phoneRegex = Regex("^[+]?[0-9]{10,15}$")

    fun isValid(phone: String): Boolean {
        val normalized = phone.replace(" ", "").replace("-", "")
        return phoneRegex.matches(normalized)
    }
}
