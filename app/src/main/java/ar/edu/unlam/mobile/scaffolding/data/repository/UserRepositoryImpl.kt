package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.dao.UserDao
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(private val userDao: UserDao) : UserRepository {
        override suspend fun getUser(id: Long): Flow<User> {
            return userDao.getUser(id).map { it.toDomain() }
        }

        override suspend fun saveUser(user: User) {
            userDao.upsert(user.toEntity())
        }
    }
