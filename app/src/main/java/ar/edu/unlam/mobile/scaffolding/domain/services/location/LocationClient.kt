package ar.edu.unlam.mobile.scaffolding.domain.services.location

import ar.edu.unlam.mobile.scaffolding.domain.models.Coordinate
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Coordinate>

    fun stopLocationUpdates()

    class LocationException(message: String) : Exception(message)
}
