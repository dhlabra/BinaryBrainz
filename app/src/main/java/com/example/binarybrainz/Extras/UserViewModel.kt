package com.example.binarybrainz.Extras

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    fun signUp(email: String, password: String, role: String, name: String, lastName: String, phone: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.signUp(email, password, role, name, lastName, phone)
                //navController.navigate("signup_admin_view_2")
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setUser(userRole: String, userName: String, userLastName: String, userPhone: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                userRepository.setUser(userRole, userName, userLastName, userPhone)
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

    private val _asesoriaList = mutableStateOf<List<Asesoria>>(emptyList())
    val asesoriaList: List<Asesoria> get() = _asesoriaList.value

    val user = mutableStateOf<User?>(null)

    // Función para obtener la lista de usuarios desde Supabase
    suspend fun loadUserNameList() {
        _userList.value = userRepository.getUserNameList()  // Usar la función existente en el repositorio
    }

    suspend fun loadUser() {
        try {
            val fetchedUser = userRepository.getUser()
            user.value = fetchedUser
        } catch (e: Exception) {
            user.value = null
        }
    }

    suspend fun updateUser(updates: Map<String, String>) {
        try {
            if (updates.isNotEmpty()) {
                userRepository.updateUser(updates)

                user.value = user.value?.copy(
                    nombre = updates["nombre"] ?: user.value?.nombre ?: "",
                    apellido = updates["apellido"] ?: user.value?.apellido ?: "",
                    celular = updates["celular"] ?: user.value?.celular ?: ""
                )
            }
        } catch (e: Exception){

        }
    }

    suspend fun loadAsesoriaList() {
        _asesoriaList.value = userRepository.getAsesoriaList()  // Usar la función existente en el repositorio
    }


    private val _citaList = mutableStateOf<List<Cita>>(emptyList())
    val citaList: List<Cita> get() = _citaList.value
    suspend fun loadCitaList() {
        _citaList.value = userRepository.getCitaList()  // Usar la función existente en el repositorio
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

    fun setAsesoria(description: String, category: String){
        isLoading.value = true
        errorMessage.value = " "
        viewModelScope.launch {
            try {
                userRepository.setAsesoria(description, category)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun setAsesoriaCompartida(asesoriaId: String, estudianteId: String) {
        isLoading.value = true
        errorMessage.value = " "
        viewModelScope.launch {
            try {
                userRepository.setAsesoriaCompartida(asesoriaId, estudianteId)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

}