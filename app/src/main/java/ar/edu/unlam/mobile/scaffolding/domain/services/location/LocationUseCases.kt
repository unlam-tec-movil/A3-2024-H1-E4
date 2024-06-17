package ar.edu.unlam.mobile.scaffolding.domain.services.location

import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface LocationUseCases {
    suspend fun startLocation()

    fun stopLocation()

    fun getLocationCoordinates(): StateFlow<List<Coordinate>>

    fun getSpeeds(): List<Float>

    fun isRunning(): Flow<Boolean>
}