package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface HomeCoordinateUIState {
    data class Success(
        val coordinateList: List<Coordinate>,
    ) : HomeCoordinateUIState

    data object Loading : HomeCoordinateUIState

    data class Error(
        val message: String,
    ) : HomeCoordinateUIState
}

@Immutable
sealed interface LocatorState {
    data object Success : LocatorState

    data object Loading : LocatorState

    data class Error(
        val message: String,
    ) : LocatorState
}

data class HomeUIState(
    val locatorState: LocatorState,
    val coordinateUIState: HomeCoordinateUIState,
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val location: LocationUseCases,
    ) : ViewModel() {
        init {
            startLocationService()
        }

        private var isRunning = location.isRunning()

        private var _uiState =
            MutableStateFlow(
                HomeUIState(
                    locatorState = LocatorState.Loading,
                    coordinateUIState = HomeCoordinateUIState.Loading,
                ),
            )

        val uiState = _uiState.asStateFlow()

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
            viewModelScope.launch {
                isRunning.collect {
                    if (it) {
                        _uiState.value =
                            _uiState.value.copy(
                                locatorState = LocatorState.Success,
                            )
                    }
                }
            }
        }

        fun getCoordinates() {
            viewModelScope.launch {
                location
                    .getLocationCoordinates()
                    .collect {
                        _uiState.value =
                            _uiState.value.copy(
                                coordinateUIState = HomeCoordinateUIState.Success(it),
                            )
                    }
            }
        }

        fun stopLocationService() {
            location.stopLocation()
            // Log.i("CNO Coordinates VIEVM", "Coordinates: ${location.locationCoordinates.value}")
        }
    }
