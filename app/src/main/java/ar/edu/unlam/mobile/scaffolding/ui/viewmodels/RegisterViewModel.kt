package ar.edu.unlam.mobile.scaffolding.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.Route
import ar.edu.unlam.mobile.scaffolding.domain.models.User
import ar.edu.unlam.mobile.scaffolding.domain.usecases.RouteUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.UserUseCases
import com.mapbox.maps.extension.style.expressions.dsl.generated.get
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(private val userService: UserUseCases) :
    ViewModel() {
    private val _userUiState = MutableStateFlow(UserUIState())
    val userUiState = _userUiState.asStateFlow()

    init {
        getUser()
    }

    fun setUser(user:User){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userService.saveUser(user)
            }
        }
    }
    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userService.getUser(1).catch {
                    _userUiState.value = _userUiState.value.copy(error = it.message.orEmpty())
                }
                .collect { user ->
                    _userUiState.value = UserUIState(user, false)
                }
            }
        }
    }

}
