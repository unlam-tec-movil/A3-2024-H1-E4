package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@Immutable
sealed interface CoordinateUIState {
    data class Success(
        val coordinateList: List<Coordinate>,
    ) : CoordinateUIState

    data object Loading : CoordinateUIState

    data class Error(
        val message: String,
    ) : CoordinateUIState
}

data class ActivityUIState(
    val coordinateUIState: CoordinateUIState,
)

@HiltViewModel
class ActivityProgressViewModel
    @Inject
    constructor(private val locationUseCases: LocationUseCases) : ViewModel() {
        private var startTime: Long = 0L
        private var isRunning: Boolean = false
        private val _uiState: MutableStateFlow<ActivityUIState> =
            MutableStateFlow(ActivityUIState(coordinateUIState = CoordinateUIState.Loading))

        @Suppress("ktlint:standard:backing-property-naming")
        private val _distanceState = MutableStateFlow(0F)

        @Suppress("ktlint:standard:backing-property-naming")
        private var _eleapsedTimeState = MutableStateFlow(0L)

        @Suppress("ktlint:standard:backing-property-naming")
        private var _speedState = MutableStateFlow(0F)

        val distanceState = _distanceState.asStateFlow()
        var uiState = _uiState.asStateFlow()
        val eleapsedTimeState = _eleapsedTimeState.asStateFlow()
        val speedAverageState = _speedState.asStateFlow()

        fun start() {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - eleapsedTimeState.value
                isRunning = true
                viewModelScope.launch {
                    while (isRunning) {
                        locationUseCases.startLocation()
                    }
                }
                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        while (isRunning) {
                            delay(1000)
                            if (isRunning) {
                                _uiState.value =
                                    ActivityUIState(CoordinateUIState.Success(locationUseCases.getLocationCoordinates().value))
                                _eleapsedTimeState.value = System.currentTimeMillis() - startTime
                                _speedState.value = getAverageSpeed()
                                _distanceState.value = locationUseCases.getDistance()

                                Log.i("Velocidad: ", _speedState.value.toString())
                            }
                        }
                    }
                }
            }
        }

        fun pause() {
            if (isRunning) {
                locationUseCases.stopLocation()
                isRunning = false
            }
        }

        fun stop() {
            if (isRunning) {
                locationUseCases.finish()
                _speedState.value = 0f
                isRunning = false
            }
        }

        private fun getAverageSpeed(): Float {
            var accumulatedSpeed = 0F
            val quantitySpeeds = locationUseCases.getSpeeds().size
            for (speed in locationUseCases.getSpeeds()) {
                accumulatedSpeed += speed
            }
            return accumulatedSpeed / quantitySpeeds
        }
    }
