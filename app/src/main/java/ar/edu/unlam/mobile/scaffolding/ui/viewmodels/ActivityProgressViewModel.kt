package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ActivityProgressViewModel
    @Inject
    constructor(private val locationUseCases: LocationUseCases) : ViewModel() {
        private var startTime: Long = 0L
        private var isRunning: Boolean = false

        @Suppress("ktlint:standard:backing-property-naming")
        private var _eleapsedTimeState = MutableStateFlow(0L)
        private var job: Job? = null

        val eleapsedTimeState = _eleapsedTimeState.asStateFlow()

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
                        delay(1000)
                        withContext(Dispatchers.Main) {
                            _eleapsedTimeState.value = System.currentTimeMillis() - startTime
                        }
                    }
                }
            }
        }

        fun stop() {
            if (isRunning) {
                Log.i(
                    "Coordenadas obtenidas",
                    "Valor= ${locationUseCases.getLocationCoordinates().value}",
                )
                locationUseCases.stopLocation()
                isRunning = false
                // job?.cancel()
            }
        }

        fun reset() {
            isRunning = false
            job?.cancel()
            _eleapsedTimeState.value = 0L
        }
    }
