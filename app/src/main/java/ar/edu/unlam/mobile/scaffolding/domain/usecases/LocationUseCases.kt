package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Coordinate
import kotlinx.coroutines.flow.StateFlow

interface LocationUseCases {
    suspend fun startLocation()

    fun stopLocation()

    fun getLocationCoordinates(): StateFlow<List<Coordinate>>

    fun getSpeeds(): List<Float>

    fun getDistance(): Float

    fun finish()
}
