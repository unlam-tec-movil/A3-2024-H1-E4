package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.domain.services.user.UserService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UserUseCases
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserDomainModule {
    @Binds
    abstract fun bindUserUseCase(userUseCaseImpl: UserService): UserUseCases
}
