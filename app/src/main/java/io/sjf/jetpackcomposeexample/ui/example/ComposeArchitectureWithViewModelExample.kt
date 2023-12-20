package io.sjf.jetpackcomposeexample.ui.example

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

// Data class representing a User
data class User(val id: Int, val name: String)

// Simulated repository that would fetch data from a data source
class UserRepository @Inject constructor() {
    fun getUsers(): List<User> {
        return listOf(User(1, "Alice"), User(2, "Bob"), User(3, "Charlie"))
    }
}

// Use case
class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() = flow {
        emit(userRepository.getUsers())
    }
}

// Sealed class representing different states of the UI
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val exception: Throwable) : UiState<Nothing>()
}

// ViewModel handle business logic and communication with the repository
// It exposes the data as StateFlow so that it can be observed by the UI
@HiltViewModel
class UserListViewModel
@Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> = _uiState

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            getUsersUseCase()
                .onStart {
                    // Emit loading state to the UI
                    _uiState.value = UiState.Loading
                }
                .catch { exception ->
                    // Emit error state to the UI
                    _uiState.value = UiState.Error(exception)
                }
                .collect { userList ->
                    // Emit success state with data to the UI
                    _uiState.value = UiState.Success(userList)
                }
        }
    }
}

// Composable
@Composable
fun ComposeArchitectureWithViewModelExample(viewModel: UserListViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            // Display a loading indicator
            Text("Loading...")
        }
        is UiState.Success<*> -> {
            // Display the list of users
            LazyColumn {
                items((uiState as UiState.Success<List<User>>).data) { user ->
                    Text("User: ${user.name}")
                }
            }
        }
        is UiState.Error -> {
            // Display an error message
            Text("An error occurred: ${(uiState as UiState.Error).exception.message}")
        }
    }
}