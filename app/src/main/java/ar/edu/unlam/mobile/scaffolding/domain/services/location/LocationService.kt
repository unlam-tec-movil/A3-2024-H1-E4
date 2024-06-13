package ar.edu.unlam.mobile.scaffolding.domain.services.location

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import dagger.hilt.android.scopes.ServiceScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

        private val _locationCoordinates = MutableStateFlow<List<Coordinate>>(emptyList())

        private val _locationSpeeds = mutableListOf<Float>()

        override fun getLocationCoordinates(): StateFlow<List<Coordinate>> = _locationCoordinates.asStateFlow()

        override fun getSpeeds(): List<Float> = _locationSpeeds

        override fun startLocation() {
            // Todo, this could be configurable
            val threeSeconds = 3L
            locationClient
                .getLocationUpdates(threeSeconds)
                .catch { exception -> exception.printStackTrace() }
                .onEach { coordinate ->
                    _locationCoordinates.value += coordinate
                    _locationSpeeds.add(coordinate.speed)
                    Log.i("CNO Location", "LAT=${coordinatesState.lastOrNull()?.latitude}")
                    Log.i("CNO Location", "LONG=${coordinatesState.lastOrNull()?.longitude}")
                    Log.i("CNO Location", "Speed: ${coordinate.speed}")
                }
        }

        override fun stopLocation() {
            locationClient.stopLocationUpdates()
        }
    }
