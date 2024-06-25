package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.models.user.User
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
    val user: User = User(1, "Juan", "Pérez", 22, 171, 75.0, 150.0, 5000, 300, 10),
    val loading: Boolean = true,
    val error: String = "",
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(private val userService: UserUseCases) : ViewModel() {
        private val _userUiState = MutableStateFlow(UserUIState())
        val userUiState = _userUiState.asStateFlow()

        init {
            getUser()
        }

        fun getUser() {
            viewModelScope.launch {
                userService.getUser(1).catch {
                    _userUiState.value = _userUiState.value.copy(error = it.message.orEmpty())
                    // TODO: Sacar e implementar una creacion de usuario por UI
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            userService.saveUser(
                                User(
                                    1,
                                    "Juan",
                                    "Pérez",
                                    22,
                                    171,
                                    75.0,
                                    150.0,
                                    5000,
                                    300,
                                    10,
                                ),
                            )
                        }
                    }
                }.collect { user ->
                    _userUiState.value = _userUiState.value.copy(user = user, loading = false)
                }
            }
        }
    }
