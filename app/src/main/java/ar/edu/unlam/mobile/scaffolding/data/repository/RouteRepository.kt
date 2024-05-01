package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.entity.Route
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    fun insertRoute(route: Route)

    fun deleteRoute(route: Route)

    fun updateRoute(route: Route)

    fun getRoute(routeId: Long): Flow<Route>

    fun getAllRoutes(): Flow<List<Route>>
}
