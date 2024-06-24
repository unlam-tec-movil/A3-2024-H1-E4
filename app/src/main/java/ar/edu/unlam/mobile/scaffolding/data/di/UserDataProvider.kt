package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.repository.UserRepository
import ar.edu.unlam.mobile.scaffolding.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataProvider {
    @Provides
    @Singleton
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository {
        return userRepository
    }
}
