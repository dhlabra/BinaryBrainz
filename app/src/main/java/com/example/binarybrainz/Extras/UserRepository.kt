package com.example.binarybrainz.Extras

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.put

class UserRepository(private val supabase: SupabaseClient, scope: CoroutineScope) {


    private val _sessionState = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    val sessionState: StateFlow<SessionStatus> get() = _sessionState

    init {
        scope.launch {
            // Listener para cambios de sesión
            supabase.auth.sessionStatus.collect { sessionStatus ->
                _sessionState.value = sessionStatus
            }
        }
    }


    suspend fun signIn(userEmail: String, userPassword: String) {
        supabase.auth.signInWith(Email){
            email = userEmail
            password = userPassword
        }
    }

    suspend fun signUp(userEmail: String, userPassword: String, userTipo: String, userNombre: String) {
        // 1. Registrar al nuevo usuario en la tabla `users`
        supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }
        supabase.auth.updateUser {
            data {
                put("nombre", userNombre)
                put("tipo", userTipo)
            }
        }
    }



    suspend fun signOut() {
        supabase.auth.signOut()
    }

}

