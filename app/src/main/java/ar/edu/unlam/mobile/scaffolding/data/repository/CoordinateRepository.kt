package ar.edu.unlam.mobile.scaffolding.data.repository

import ar.edu.unlam.mobile.scaffolding.data.database.entity.Coordinate
import kotlinx.coroutines.flow.Flow

interface CoordinateRepository {
    fun insertCoordinate(coordinate: Coordinate)

    fun deleteCoordinate(coordinate: Coordinate)

    fun updateCoordinate(coordinate: Coordinate)

    fun getCoordinate(coordinateId: Long): Flow<Coordinate>

    fun getAllCoordinates(): Flow<List<Coordinate>>
}
