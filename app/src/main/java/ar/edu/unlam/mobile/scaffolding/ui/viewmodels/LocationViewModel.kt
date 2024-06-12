package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationService
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

// sealed interface LocationStates {
//    data class Success(val coordinates: Coordinate) : LocationStates
//
//    data object Loading : LocationStates
//
//    data class Error(val messageError: String) : LocationStates
// }
//
// data class LocationUIState(val locationState: LocationStates)

@HiltViewModel
class LocationViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val locationService: LocationService,
    ) :
    ViewModel() {
        private val locationState =
            MutableStateFlow(Coordinate(0.0, 0.0))
        val locationUiState = locationState.asStateFlow()

        fun getLocationCoordinates(): MutableList<Coordinate> = LocationServiceImpl.coordinatesState

        fun getSpeed(): Float {
            var accumulatedSpeed: Float = 0F
            val quantitySpeeds = locationService.locationSpeeds.size
            for (speed in locationService.getSpeeds()) {
                accumulatedSpeed += speed
            }
            return accumulatedSpeed / quantitySpeeds
        }

        fun startLocationService() {
            Intent(context, LocationServiceImpl::class.java).apply {
                action = LocationServiceImpl.ACTION_START
                context.startService(this)
            }
        }

        fun stopLocationService() {
            Intent(context, LocationServiceImpl::class.java).apply {
                action = LocationServiceImpl.ACTION_STOP
                context.startService(this)
            }

            Log.i("CNO Coordinates VIEVM", "Coordinates: ${locationService.locationCoordinates.value}")
        }
    }
