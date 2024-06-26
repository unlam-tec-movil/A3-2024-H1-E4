package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import ar.edu.unlam.mobile.scaffolding.domain.usecases.RouteUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class UserUIState(
    val user: User = MockEntities.user,
    val loading: Boolean = true,
    val error: String = "",
)

data class RouteUIState(
    val route: Route? = MockEntities.route,
    val loading: Boolean = true,
    val error: String = "",
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(private val userService: UserUseCases, private val routeService: RouteUseCases) :
    ViewModel() {
        private val _userUiState = MutableStateFlow(UserUIState())
        val userUiState = _userUiState.asStateFlow()
        private val _routeUiState = MutableStateFlow(RouteUIState())
        val routeUiState = _routeUiState.asStateFlow()

        init {
            getUser()
            getLatestRoute()
        }

        fun getUser() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    userService.getUser(1).catch {
                        _userUiState.value = _userUiState.value.copy(error = it.message.orEmpty())

                    }.collect { user ->
                        _userUiState.value = _userUiState.value.copy(user = user, loading = false)
                    }
                }
            }
        }

        private fun getLatestRoute() {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    routeService.getLatestRoute().catch {
                        _routeUiState.value = _routeUiState.value.copy(error = it.message.orEmpty())
                    }.collect { route ->
                        _routeUiState.value = _routeUiState.value.copy(route = route, loading = false)
                    }
                }
            }
        }
    }
