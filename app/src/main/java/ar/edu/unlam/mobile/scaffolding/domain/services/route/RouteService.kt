package ar.edu.unlam.mobile.scaffolding.domain.services.route

import ar.edu.unlam.mobile.scaffolding.data.repository.RouteRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.domain.usecases.RouteUseCases
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RouteService
    @Inject
    constructor(
        private val routeRepository: RouteRepository,
    ) : RouteUseCases {
        override suspend fun getRoute(id: Long): Flow<Route?> {
            return routeRepository.getRoute(id)
        }

        override suspend fun saveRoute(route: Route) {
            routeRepository.saveRoute(route)
        }

        override suspend fun getAllRoutes(): Flow<List<Route?>> {
            return routeRepository.getAllRoutes()
        }

        override suspend fun getLatestRoute(): Flow<Route?> {
            return routeRepository.getLatestRoute()
        }

        override suspend fun getBestRoute(): Flow<Route?> {
            return routeRepository.getBestRoute()
        }
    }
