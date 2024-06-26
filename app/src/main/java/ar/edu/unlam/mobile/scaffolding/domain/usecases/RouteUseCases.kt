package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import kotlinx.coroutines.flow.Flow

interface RouteUseCases {
    suspend fun getRoute(id: Long): Flow<Route?>

    suspend fun saveRoute(route: Route)

    suspend fun getAllRoutes(): Flow<List<Route?>>

    suspend fun getLatestRoute(): Flow<Route?>

    suspend fun getBestRoute(): Flow<Route?>
}
