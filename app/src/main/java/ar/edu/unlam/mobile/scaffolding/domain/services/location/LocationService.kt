package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationService
    @Inject
    constructor(
        private val locationClient: LocationClient,
    ) : LocationUseCases {
        //        locationClient =
        //            DefaultLocationClient(
        //                applicationContext,
        //                LocationServices.getFusedLocationProviderClient(applicationContext),
        //            )
        private val running = MutableStateFlow(false)
        private val _locationCoordinates = MutableStateFlow<MutableList<Coordinate>>(mutableListOf())
        private val _speeds = mutableListOf<Float>()

        override fun getLocationCoordinates(): StateFlow<List<Coordinate>> = _locationCoordinates.asStateFlow()

        override fun getSpeeds(): List<Float> = _speeds

        override fun isRunning(): Flow<Boolean> = running

        override suspend fun startLocation() {
            val threeSeconds = 3L
            locationClient
                .getLocationUpdates(threeSeconds)
                .collect { coordinate ->
                    running.value = true
                    _locationCoordinates.value.add(coordinate)
                    _speeds.add(coordinate.speed)
                    Log.i("CNO Location", "latitude: ${coordinate.latitude}")
                    Log.i("CNO Location", "longitude: ${coordinate.longitude}")
                    Log.i("CNO Location", "Speed: ${coordinate.speed}")
                } // .onCompletion { running.value = true }
        }

        override fun stopLocation() {
            running.value = false
            _locationCoordinates.value = mutableListOf()
            locationClient.stopLocationUpdates()
        }
    }
