package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.domain.usecases.LocationUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.RouteUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UserUseCases
import com.mapbox.geojson.utils.PolylineUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.Date
import java.util.concurrent.TimeUnit
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

/**
 * MET es la Unidad Metabólica de Reposo.
 * Es una medida utilizada en fisiología del ejercicio para cuantificar
 * la cantidad de energia que se gasta durante distintas actividades físicas
 */
private const val MET = 13.0

@HiltViewModel
class ActivityProgressViewModel
    @Inject
    constructor(
        private val locationUseCases: LocationUseCases,
        private val routeUseCases: RouteUseCases,
        private val userUseCases: UserUseCases,
    ) : ViewModel() {
        private var startTime: Long = 0L
        private var isRunning: Boolean = false
        private val _uiState: MutableStateFlow<ActivityUIState> =
            MutableStateFlow(ActivityUIState(coordinateUIState = CoordinateUIState.Loading))

        @Suppress("ktlint:standard:backing-property-naming")
        private val _caloriesState = MutableStateFlow(0.0)

        @Suppress("ktlint:standard:backing-property-naming")
        private val _distanceState = MutableStateFlow(0F)

        @Suppress("ktlint:standard:backing-property-naming")
        private var _elapsedTimeState = MutableStateFlow(0L)

        @Suppress("ktlint:standard:backing-property-naming")
        private var _speedState = MutableStateFlow(0F)

        val distanceState = _distanceState.asStateFlow()
        var uiState = _uiState.asStateFlow()
        val elapsedTimeState = _elapsedTimeState.asStateFlow()
        val speedAverageState = _speedState.asStateFlow()

        fun start() {
            if (!isRunning) {
                startTime = System.currentTimeMillis() - elapsedTimeState.value
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
                                _elapsedTimeState.value = System.currentTimeMillis() - startTime
                                _speedState.value = getAverageSpeed()
                                _distanceState.value = locationUseCases.getDistance()
                            }
                        }
                    }
                }
            }
        }

        fun getCalories(weightUser: Double): StateFlow<Double> {
            val timeInHours =
                TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(_elapsedTimeState.value),
                ).toDouble() / 60 / 60

            if (locationUseCases.getSpeeds().lastOrNull() != 0f) {
                _caloriesState.value = (MET * weightUser * timeInHours)
            }

            return _caloriesState.asStateFlow()
        }

        fun pause() {
            if (isRunning) {
                locationUseCases.stopLocation()
                isRunning = false
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun stop(userId: Long) {
            if (!isRunning) {
                val polylineEncoded: String
                val polylineEncodedAux: String =
                    PolylineUtils.encode(
                        locationUseCases.getLocationCoordinates().value.map { it.toPoint() },
                        5,
                    )

                polylineEncoded =
                    if (polylineEncodedAux.contains("?")) {
                        polylineEncodedAux.replace("?", "63")
                    } else {
                        polylineEncodedAux
                    }

                Log.i("POLYLINEA CODIFI", "VALOR = $polylineEncoded")

                val maxSpeed =
                    if (locationUseCases.getSpeeds().isNotEmpty()) {
                        locationUseCases.getSpeeds()
                            .max()
                    } else {
                        0f
                    }
                val date: Date = Date.from(Instant.now())
                val route =
                    Route(
                        userId = userId,
                        durationSeconds = _elapsedTimeState.value,
                        calories = _caloriesState.value.toLong(),
                        maxSpeed = maxSpeed.toDouble(),
                        avgSpeed = _speedState.value.toDouble(),
                        distance = _distanceState.value.toDouble(),
                        date = date,
                        coordinates = polylineEncoded,
                    )

                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        val lastRoute = routeUseCases.getLatestRoute().first()
                        val shouldIncreaseDays =
                            if (lastRoute == null) {
                                true
                            } else {
                                TimeUnit.MILLISECONDS.toDays(route.date.time - lastRoute.date.time) != 0L
                            }
                        var user = userUseCases.getUser(userId).first()
                        var modifiedUser =
                            user.copy(
                                calories = user.calories + route.calories.toInt(),
                                kilometers = user.kilometers + route.distance,
                                minutes =
                                    user.minutes +
                                        TimeUnit.MILLISECONDS.toMinutes(route.durationSeconds)
                                            .toInt(),
                                days = if (shouldIncreaseDays) user.days + 1 else user.days,
                            )
                        userUseCases.saveUser(modifiedUser)
                    }
                }

                viewModelScope.launch {
                    withContext(Dispatchers.IO) {
                        routeUseCases.saveRoute(route)
                    }
                }

                resetState()
            }
        }

        fun cancel() {
            resetState()
        }

        private fun getAverageSpeed(): Float {
            var accumulatedSpeed = 0F
            val quantitySpeeds = locationUseCases.getSpeeds().size
            for (speed in locationUseCases.getSpeeds()) {
                accumulatedSpeed += speed
            }
            return if ((accumulatedSpeed / quantitySpeeds).isNaN()) 0.0f else (accumulatedSpeed / quantitySpeeds)
        }

        private fun resetState() {
            locationUseCases.finish()
            _speedState.value = 0f
            _caloriesState.value = 0.0
            _distanceState.value = 0F
            _elapsedTimeState.value = 0L
            _speedState.value = 0F
            isRunning = false
        }
    }
