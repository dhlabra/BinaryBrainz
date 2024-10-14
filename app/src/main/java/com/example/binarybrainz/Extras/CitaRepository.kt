package com.example.binarybrainz.Extras

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CitaRepository(private val supabase: SupabaseClient, scope: CoroutineScope) {


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

    suspend fun setCita(client_phone: String, asesoria_id: String, date: String, time_slot: String) {
        val idClient = supabase.auth.retrieveUserForCurrentSession().id
        val setCita = mapOf("client_id" to idClient, "client_phone" to client_phone, "asesoria_id" to asesoria_id, "date" to date, "time_slot" to time_slot, "status" to "pendiente")
        supabase.from("citas")
            .insert(setCita)
    }

    suspend fun getCita(): Cita? {
        val idClient = supabase.auth.retrieveUserForCurrentSession().id
        val result = supabase.from("citas").select{
            filter {
                eq("client_id", idClient)
            }
        }.decodeSingleOrNull<Cita>()
        return result
    }
}