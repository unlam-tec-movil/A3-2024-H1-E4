package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.dao.RouteDao
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RouteRepositoryImpl
    @Inject
    constructor(private val routeDao: RouteDao) :
    RouteRepository {
        override fun saveRoute(route: Route) {
            routeDao.upsert(route.toEntity())
        }

        override fun deleteRoute(route: Route) {
            routeDao.delete(route.toEntity())
        }

        override fun getRoute(routeId: Long): Flow<Route?> {
            return routeDao.getRoute(routeId).map { it.toDomain() }
        }

        override fun getAllRoutes(): Flow<List<Route?>> {
            return routeDao.getAllRoutes().map { it.map { it.toDomain() } }
        }

        override fun getLatestRoute(): Flow<Route?> {
            return routeDao.getLatestRoute().map { it.toDomain() }
        }

        override fun getBestRoute(): Flow<Route?> {
            return routeDao.getBestRoute().map { it.toDomain() }
        }
    }
