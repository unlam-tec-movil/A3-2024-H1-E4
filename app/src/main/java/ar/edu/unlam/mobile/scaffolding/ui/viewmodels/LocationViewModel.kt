package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationService
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

sealed interface LocationStates {
    data class Success(val coordinates: Coordinate) : LocationStates

    data object Loading : LocationStates

    data class Error(val messageError: String) : LocationStates
}

data class LocationUIState(val locationState: LocationStates)

@HiltViewModel
class LocationViewModel
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) :
    ViewModel() {
        private var locationService: LocationService = LocationServiceImpl()
        private val locationState =
            MutableStateFlow(LocationUIState(LocationStates.Success(Coordinate(0.0, 0.0))))
        val locationUiState = locationState.asStateFlow()

        fun startLocationService() {
            Intent(context, LocationServiceImpl::class.java).apply {
                action = LocationServiceImpl.ACTION_START
                context.startService(this)
            }

            locationState.value =
                LocationUIState(locationState = LocationStates.Success(locationService.getCurrentLocation()))
        }

        fun stopLocationService() {
            Intent(context, LocationServiceImpl::class.java).apply {
                action = LocationServiceImpl.ACTION_STOP
                context.startService(this)
            }
        }

        fun getCurrentLocation(): Coordinate = this.locationService.getCurrentLocation()
    }
