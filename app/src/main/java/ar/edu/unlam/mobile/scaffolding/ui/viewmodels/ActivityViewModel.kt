package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import ar.edu.unlam.mobile.scaffolding.domain.models.location.Coordinate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class ActivityViewModel
    @Inject
    constructor() : ViewModel() {
        private val coordinates = MutableStateFlow(CoordinateUIState.Loading)

        private val _uiState = MutableStateFlow(ActivityUIState(coordinates.value))

        val uiState = _uiState.asStateFlow()
    }
