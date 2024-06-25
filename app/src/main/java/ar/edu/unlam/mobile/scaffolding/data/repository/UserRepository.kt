package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(id: Long): Flow<User>

    suspend fun saveUser(user: User)
}
