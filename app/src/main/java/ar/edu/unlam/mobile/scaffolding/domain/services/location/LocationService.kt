package ar.edu.unlam.mobile.scaffolding.domain.services.location

import androidx.collection.MutableFloatList
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import kotlinx.coroutines.flow.MutableStateFlow

interface LocationService {
    val locationCoordinates: MutableStateFlow<MutableList<Coordinate>>
    val locationSpeeds: MutableFloatList

    fun startLocation()

    fun stopLocation()

    fun getLocationCoordinates(): List<Coordinate>

    fun getSpeeds(): List<Float>
}
