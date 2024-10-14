package com.example.binarybrainz.Extras

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.gotrue.SessionStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CitaViewModel( private val citaRepository: CitaRepository) : ViewModel(){

    // Estado de sesión observable por la UI
    val sessionState: StateFlow<SessionStatus> = citaRepository.sessionState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SessionStatus.LoadingFromStorage
    )

    // Estados adicionales para controlar la UI durante el proceso de autenticación
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf("")

    fun setCita(client_phone: String, asesoria_id: String, date: String, time_slot: String) {
        isLoading.value = true
        errorMessage.value = ""
        viewModelScope.launch {
            try {
                citaRepository.setCita(client_phone, asesoria_id, date, time_slot)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

}