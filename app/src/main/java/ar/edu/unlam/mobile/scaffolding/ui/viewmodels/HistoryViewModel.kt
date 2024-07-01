package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.domain.usecases.RouteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class HistoryUiState(
    val routes: List<Route?> = listOf(MockEntities.route),
    val loading: Boolean = true,
    val error: String = "",
)

@HiltViewModel
class HistoryViewModel
    @Inject
    constructor(private val routeService: RouteUseCases) :
    ViewModel() {
        private val _historyUiState = MutableStateFlow(HistoryUiState())

        val historyUiState = _historyUiState.asStateFlow()

        init {
            getAllRoutes()
        }

        private fun getAllRoutes() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    routeService.getAllRoutes().catch {
                        _historyUiState.value = _historyUiState.value.copy(error = it.message.orEmpty())
                    }.collect { routes ->
                        _historyUiState.value =
                            _historyUiState.value.copy(routes = routes, loading = false)
                    }
                }
            }
        }
    }
