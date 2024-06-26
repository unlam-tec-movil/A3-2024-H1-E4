package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.domain.services.route.RouteService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.RouteUseCases
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RouteDomainModule {
    @Binds
    abstract fun bindRouteUseCase(routeUseCaseImpl: RouteService): RouteUseCases
}
