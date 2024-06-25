package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.usecases.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

        var uiState = _uiState.asStateFlow()

        @Suppress("ktlint:standard:backing-property-naming")
        private var _eleapsedTimeState = MutableStateFlow(0L)
        private var _speedState = MutableStateFlow(0F)

        val eleapsedTimeState = _eleapsedTimeState.asStateFlow()
        val speedState = _speedState.asStateFlow()

        fun getSpeed(): Float {
            var accumulatedSpeed: Float = 0F
            val quantitySpeeds = locationUseCases.getSpeeds().size
            for (speed in locationUseCases.getSpeeds()) {
                accumulatedSpeed += speed
            }
            return accumulatedSpeed / quantitySpeeds
        }

        fun getCoordinates(): StateFlow<List<Coordinate>> = locationUseCases.getLocationCoordinates()

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
                                _eleapsedTimeState.value = System.currentTimeMillis() - startTime
                                _speedState.value = getSpeed()
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
                Log.i(
                    "Coordenadas obtenidas",
                    "Valor= ${locationUseCases.getLocationCoordinates().value}",
                )
                locationUseCases.stopLocation()
                _speedState.value = 0f
                isRunning = false
            }
        }
    }
