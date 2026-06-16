package ru.glebik.mtsproject.core.session

import android.content.Context
import ru.glebik.mtsproject.feature.auth.domain.model.User
import javax.inject.Inject
import androidx.core.content.edit

class UserSession @Inject constructor(
    context: Context,
) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit {
            putString(KEY_ID, user.id)
                .putString(KEY_NAME, user.fullName)
                .putString(KEY_EMAIL, user.email)
        }
    }

    fun getUser(): User? {
        val id = prefs.getString(KEY_ID, null) ?: return null
        val name = prefs.getString(KEY_NAME, null)
        val email = prefs.getString(KEY_EMAIL, null)

        return User(
            id = id,
            fullName = name.orEmpty(),
            email = email.orEmpty(),
            phone = "",
            status = "",
            createdAt = ""
        )
    }

    fun clear() {
        prefs.edit { clear() }
    }

    fun isLoggedIn(): Boolean {
        return getUser() != null
    }

    private companion object {
        const val KEY_ID = "key_id"
        const val KEY_NAME = "key_name"
        const val KEY_EMAIL = "key_email"
    }
}
