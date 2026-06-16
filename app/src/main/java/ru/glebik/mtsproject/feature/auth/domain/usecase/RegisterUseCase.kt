package ru.glebik.mtsproject.feature.auth.domain.usecase

import ru.glebik.mtsproject.core.session.UserSession
import ru.glebik.mtsproject.feature.auth.domain.AuthRepository
import javax.inject.Inject

interface RegisterUseCase {
    suspend operator fun invoke(
        nickname: String,
        email: String,
        phone: String,
    ): Result<Unit>
}

class RegisterUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val userSession: UserSession,
) : RegisterUseCase {

    override suspend operator fun invoke(
        nickname: String,
        email: String,
        phone: String,
    ): Result<Unit> {
        return authRepository.register(
            nickname = nickname,
            email = email,
            phone = phone,
        ).mapCatching { user ->
            userSession.saveUser(user.id)
        }
    }
}
