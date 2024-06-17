package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import ar.edu.unlam.mobile.scaffolding.domain.services.location.LocationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

        fun startLocationService() {
            Log.i("Inicio Location", "Valor=$isRunning")

            viewModelScope.launch {
                location.startLocation()
                Log.i("VIEWMODEL SCOPE", "ENTRO A LA FUNCION START LOCATION")
                isRunning.catch { e ->
                    Log.i(
                        "FUNCION STARLOC",
                        "Message: ${e.message}, /" +
                            "${e.cause?.message}",
                    )
                }
                    .collect {
                        Log.i("COLLECTANDO bloque IF", "Valor= $it")
                        if (it) {
                            _uiState.value =
                                _uiState.value.copy(
                                    locatorState = LocatorState.Success,
                                    coordinateUIState =
                                        HomeCoordinateUIState.Success(
                                            coordinateList = location.getLocationCoordinates().value,
                                        ),
                                )
                        } else {
                            Log.i("COLLECTANDO BLOQUE ELSE", "Valor= $it")
                            location.startLocation()
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
            Log.i("COORDENADAS STOP LOC", "VALOR= ${location.getLocationCoordinates().value}")
            location.stopLocation()
            // Log.i("CNO Coordinates VIEVM", "Coordinates: ${location.locationCoordinates.value}")
        }
    }
