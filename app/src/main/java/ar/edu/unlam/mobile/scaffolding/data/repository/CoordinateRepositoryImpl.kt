package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.dao.CoordinateDao
import ar.edu.unlam.mobile.scaffolding.data.database.entity.Coordinate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//TODO: hacer mapeos de entidad a dominio y viceversa según el método
class CoordinateRepositoryImpl @Inject constructor(private val coordinateDao: CoordinateDao) :
    CoordinateRepository {

    override fun insertCoordinate(coordinate: Coordinate) {
        coordinateDao.insert(coordinate)
    }

    override fun deleteCoordinate(coordinate: Coordinate) {
        coordinateDao.delete(coordinate)
    }

    override fun updateCoordinate(coordinate: Coordinate) {
        coordinateDao.update(coordinate)
    }

    override fun getCoordinate(coordinateId: Long): Flow<Coordinate> {
        return coordinateDao.getCoordinate(coordinateId)
    }

    override fun getAllCoordinates(): Flow<List<Coordinate>> {
        return coordinateDao.getAllCoordinates()
    }
}