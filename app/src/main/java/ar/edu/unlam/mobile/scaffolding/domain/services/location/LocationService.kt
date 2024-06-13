package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ServiceScoped
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
        private val _locationCoordinates = MutableStateFlow<List<Coordinate>>(emptyList())
        private val _speeds = mutableListOf<Float>()

        override fun getLocationCoordinates(): StateFlow<List<Coordinate>> = _locationCoordinates.asStateFlow()

        override fun getSpeeds(): List<Float> = _speeds

        override fun isRunning(): Flow<Boolean> = running

        override fun startLocation() {
            // Todo, this could be configurable
            val threeSeconds = 3L
            locationClient
                .getLocationUpdates(threeSeconds)
                .onEach { coordinate ->
                    running.value = true
                    _locationCoordinates.value += coordinate
                    _speeds.add(coordinate.speed)
                    Log.i("CNO Location", "Speed: ${coordinate.speed}")
                }.onCompletion { running.value = false }
        }

        override fun stopLocation() {
            locationClient.stopLocationUpdates()
        }
    }
