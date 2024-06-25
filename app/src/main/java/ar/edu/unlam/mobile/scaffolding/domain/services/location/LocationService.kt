package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.usecases.LocationUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationService
    @Inject
    constructor(
        private val locationClient: LocationClient,
    ) : LocationUseCases {
        private val _locationCoordinates = MutableStateFlow<MutableList<Coordinate>>(mutableListOf())
        private var _speeds = mutableListOf<Float>()
        private val distanceTrackingState = MutableStateFlow(0F)

        override fun getLocationCoordinates(): StateFlow<List<Coordinate>> = _locationCoordinates.asStateFlow()

        override fun getSpeeds(): List<Float> = _speeds

        override fun getDistance(): Float = distanceTrackingState.value

        override fun finish() {
            _locationCoordinates.value = mutableListOf()
            _speeds = mutableListOf()
        }

        override suspend fun startLocation() {
            val oneSecond = 1L
            locationClient
                .getLocationUpdates(oneSecond)
                .collect { coordinate ->
                    _locationCoordinates.value.add(coordinate)
                    _speeds.add(coordinate.speed)
                    distanceTrackingState.value = coordinate.distance
                    Log.i("CNO Location", "latitude: ${coordinate.latitude}")
                    Log.i("CNO Location", "longitude: ${coordinate.longitude}")
                    Log.i("CNO Location", "Speed: ${coordinate.speed}")
                }
        }

        override fun stopLocation() {
            locationClient.stopLocationUpdates()
        }
    }
