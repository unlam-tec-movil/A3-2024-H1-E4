package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserUseCases {
    suspend fun getUser(id: Long): Flow<User>

    suspend fun saveUser(user: User)
}
