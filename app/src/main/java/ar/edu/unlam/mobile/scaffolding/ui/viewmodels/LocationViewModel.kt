package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        private val location: LocationUseCases,
    ) : ViewModel() {
        init {
            startLocationService()
        }

        private val locationState =
            MutableStateFlow(Coordinate(0.0, 0.0, 0f))
        val locationUiState = locationState.asStateFlow()

        fun getLocationCoordinates(): StateFlow<List<Coordinate>> = location.getLocationCoordinates()

        fun getSpeed(): Float {
            var accumulatedSpeed: Float = 0F
            val quantitySpeeds = location.getSpeeds().size
            for (speed in location.getSpeeds()) {
                accumulatedSpeed += speed
            }
            return accumulatedSpeed / quantitySpeeds
        }

        private fun startLocationService() {
            location.startLocation()
        }

        fun stopLocationService() {
            location.stopLocation()
            Log.i("CNO Coordinates VIEVM", "Coordinates: ${location.locationCoordinates.value}")
        }
    }
