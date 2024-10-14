package com.example.binarybrainz.Extras

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Time

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

    suspend fun signUp(userEmail: String, userPassword: String) {
        // 1. Registrar al nuevo usuario en la tabla `users`
        supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }

    }

    suspend fun setUser(userRole: String, userName: String, userLasName: String, userPhone: String) {
        val user = supabase.auth.retrieveUserForCurrentSession()
        val setRole = mapOf("id" to user.id, "role" to userRole, "nombre" to userName, "apellido" to userLasName, "celular" to userPhone)
        supabase.from("perfil")
            .insert(setRole)
    }

    suspend fun setRole(userRole: String, userid: String) {
        val setRole = mapOf("role" to userRole)
        supabase.from("perfil")
            .update(setRole) {
                filter {
                    eq("id", userid)
                }
            }
    }

    suspend fun getUserRole(): String {
        //recuperamos los datos de la sesion actual
        val user = supabase.auth.retrieveUserForCurrentSession()
        val result = supabase.from("perfil").select(){
            filter {
                eq("id", user.id )
            }
        }.decodeSingleOrNull<User>()
        return result?.role?: "empty"
    }

    suspend fun getUserNameList(): List<User> {
        val nameList = supabase.from("perfil").select(columns = Columns.list("id, role, nombre, apellido, celular")).decodeList<User>()
        return nameList
    }


    suspend fun signOut() {
        supabase.auth.signOut()
    }

}

