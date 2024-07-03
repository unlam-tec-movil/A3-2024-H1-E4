package ar.edu.unlam.mobile.scaffolding.data.di

import ar.edu.unlam.mobile.scaffolding.data.repository.RouteRepository
import ar.edu.unlam.mobile.scaffolding.data.repository.RouteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RouteDataProvider {
    @Provides
    @Singleton
    fun provideRouteRepository(userRepository: RouteRepositoryImpl): RouteRepository {
        return userRepository
    }
}
