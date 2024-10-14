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

class AsesoriaRepository(private val supabase: SupabaseClient, scope: CoroutineScope) {

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

    // Función para crear una nueva asesoría
    suspend fun setAsesoria(
        title: String,
        description: String,
        category: String,
        created_at: String,
        status: String
    ) {
        val setAsesoria = mapOf(
            "title" to title,
            "description" to description,
            "category" to category,
            "created_at" to created_at,
            "status" to status
        )
        supabase.from("asesorias")
            .insert(setAsesoria)
    }

    // Función para obtener las asesorías del usuario actual
    suspend fun getAsesorias(): List<Asesoria> {
        val id = supabase.auth.retrieveUserForCurrentSession().id
        val result = supabase.from("asesorias").select() {
            filter {
                eq("id", id)
            }
        }.decodeList<Asesoria>()
        return result
    }

    // Función para editar la fecha y hora de una asesoría
    suspend fun updateAsesoriaDateTime(asesoriaId: String, newDate: String, newTime: String) {
        val updateAsesoria = mapOf("date" to newDate, "time" to newTime)
        supabase.from("asesorias")
            .update(updateAsesoria) {
                filter {
                    eq("id", asesoriaId)
                }
            }
    }

    // Función para obtener la lista completa de asesorías (para abogados)
    suspend fun getAsesoriaList(): List<Asesoria> {
        return supabase.from("asesorias").select().decodeList<Asesoria>()
    }

    // Función para obtener las asesorías pendientes
    suspend fun getAsesoriasPendientes(): List<Asesoria> {
        return supabase.from("asesorias").select() {
            filter {
                eq("status", "pendiente")
            }
        }.decodeList<Asesoria>()
    }

    // Función para obtener las asesorías terminadas
    suspend fun getAsesoriasTerminadas(): List<Asesoria> {
        return supabase.from("asesorias").select() {
            filter {
                eq("status", "terminada")
            }
        }.decodeList<Asesoria>()
    }

    // Función para obtener todas las asesorías con estatus "pendiente"
    suspend fun getEstatusPendiente(): List<Asesoria> {
        return supabase.from("asesorias").select() {
            filter {
                eq("status", "pendiente")
            }
        }.decodeList<Asesoria>()
    }

    // Función para obtener todas las asesorías con estatus completo
    suspend fun getEstatusCompleto(): List<Asesoria> {
        return supabase.from("asesorias").select() {
            filter {
                eq("status", "completo")
            }
        }.decodeList<Asesoria>()
    }
}
