package com.example.binarybrainz.Extras

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel( private val userRepository: UserRepository) : ViewModel(){

    // Estado de sesión observable por la UI
    val sessionState: StateFlow<SessionStatus> = userRepository.sessionState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionStatus.LoadingFromStorage
    )

    // Estados adicionales para controlar la UI durante el proceso de autenticación
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    var userRole = mutableStateOf("")
    init {
        viewModelScope.launch {
            sessionState.collect { status ->
                if (status is SessionStatus.Authenticated) {
                    userRole.value = userRepository.getUserRole()
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.signIn(email, password)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun signUp(email: String, password: String, navController: NavController) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.signUp(email, password)
                navController.navigate("signup_admin_view_2")
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setUser(userRole: String, userName: String, userLasName: String, userPhone: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.setUser(userRole, userName, userLasName, userPhone)
            }catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setRole(role: String, id: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.setRole(role, id)
            }catch (e: Exception) {
                errorMessage.value = e.message ?: "Unkown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    private val _userList = mutableStateOf<List<User>>(emptyList())
    val userList: List<User> get() = _userList.value

    // Función para obtener la lista de usuarios desde Supabase
    suspend fun loadUserNameList() {
        _userList.value = userRepository.getUserNameList()  // Usar la función existente en el repositorio
    }

    fun signOut() {
        viewModelScope.launch {
            userRepository.signOut()
        }
    }

    fun setCita(fecha: String, hora: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.setCita(fecha, hora)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setAsesoria(description: String, category: String, created_at: String, status: String){
        isLoading.value = true
        errorMessage.value = " "
        viewModelScope.launch {
            try {
                userRepository.setAsesoria(description, category, created_at, status)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

}