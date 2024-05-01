package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.dao.RouteDao
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Route
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO: hacer mapeos de entidad a dominio y viceversa según el método
class RouteRepositoryImpl @Inject constructor(private val routeDao: RouteDao) :
    RouteRepository {

    override fun insertRoute(route: Route) {
        routeDao.insert(route)
    }

    override fun deleteRoute(route: Route) {
        routeDao.delete(route)
    }

    override fun updateRoute(route: Route) {
        routeDao.update(route)
    }

    override fun getRoute(routeId: Long): Flow<Route> {
        return routeDao.getRoute(routeId)
    }

    override fun getAllRoutes(): Flow<List<Route>> {
        return routeDao.getAllRoutes()
    }
}