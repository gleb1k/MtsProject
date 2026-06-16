package ru.glebik.mtsproject.core.session

import android.content.Context
import javax.inject.Inject
import androidx.core.content.edit

class UserSession @Inject constructor(
    context: Context,
) {

    private val prefs = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(userId: String) {
        prefs.edit {
            putString(KEY_USER_ID, userId)
        }
    }

    fun getUser(): String? {
        return prefs.getString(KEY_USER_ID, null)
    }

    fun clear() {
        prefs.edit { clear() }
    }

    fun isLoggedIn(): Boolean {
        return getUser() != null
    }

    private companion object {
        const val KEY_USER_ID = "key_user_id"
    }
}
