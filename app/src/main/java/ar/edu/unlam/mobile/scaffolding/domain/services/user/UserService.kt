package ar.edu.unlam.mobile.scaffolding.domain.services.user

import ar.edu.unlam.mobile.scaffolding.data.repository.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.user.User
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UserUseCases
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserService
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : UserUseCases {
        override suspend fun getUser(id: Long): Flow<User> {
            return userRepository.getUser(id)
        }

        override suspend fun saveUser(user: User) {
            return userRepository.saveUser(user)
        }
    }
