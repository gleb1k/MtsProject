package ru.glebik.mtsproject.feature.auth.domain.usecase

import jakarta.inject.Inject
import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.auth.domain.AuthRepository

interface LoginUseCase {
    suspend operator fun invoke(
        nickname: String,
        login: String,
    ): Result<Unit>
}

class LoginUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val userSession: UserSession,
) : LoginUseCase {

    override suspend operator fun invoke(
        nickname: String,
        login: String,
    ): Result<Unit> {
        return authRepository.getUsers()
            .mapCatching { users ->
                val user = users.firstOrNull { u ->
                    u.fullName.equals(nickname, ignoreCase = true) &&
                            (u.email.equals(login, ignoreCase = true) ||
                                    u.phone.equals(login, ignoreCase = true))
                } ?: throw IllegalArgumentException("Пользователь не найден")

                userSession.saveUser(user.id)
            }
    }
}
