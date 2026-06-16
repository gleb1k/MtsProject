package ru.glebik.mtsproject.feature.auth.domain.validator

import android.util.Patterns

object EmailValidator {

    fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(email.trim())
            .matches()
    }
}
