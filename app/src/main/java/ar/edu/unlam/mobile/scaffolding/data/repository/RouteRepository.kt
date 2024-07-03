package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import kotlinx.coroutines.flow.Flow

interface RouteRepository {
    fun saveRoute(route: Route)

    fun deleteRoute(route: Route)

    fun getRoute(routeId: Long): Flow<Route?>

    fun getAllRoutes(): Flow<List<Route?>>

    fun getLatestRoute(): Flow<Route?>

    fun getBestRoute(): Flow<Route?>
}
