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

    suspend fun signUp(userEmail: String, userPassword: String, role: String, name: String, lastName: String, phone: String) {
        // 1. Registrar al nuevo usuario en la tabla `users`
        supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
        }
        setUser(role, name, lastName, phone)
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

    suspend fun getUser(): User {
        val userId = supabase.auth.retrieveUserForCurrentSession().id
        val user = supabase.from("perfil").select(){
            filter {
                eq("id", userId)
            }
        }.decodeSingle<User>()
        return user
    }

    suspend fun updateUser(updates: Map<String, String>) {
        val userId = supabase.auth.retrieveUserForCurrentSession().id
        supabase.from("perfil")
            .update(updates) {
                filter {
                    eq("id", userId)
                }
            }
    }

    suspend fun signOut() {
        supabase.auth.signOut()
    }

    suspend fun setCita(fecha: String, hora: String) {
        val idClient = supabase.auth.retrieveUserForCurrentSession().id
        val userInfo = supabase
            .from("perfil")
            .select(){
            filter {
                eq("id", idClient)
            }
        }.decodeSingleOrNull<User>()
        val phone = userInfo?.celular
        val asesoriaInfo = supabase
            .from("asesorias")
            .select(){
                filter {
                    eq("cliente_id", idClient)
                }
            }.decodeSingleOrNull<Asesoria>()

        val idAsesoria = asesoriaInfo?.id.toString()

        val citaInfo = mapOf("client_phone" to phone, "status" to "pendiente", "client_id" to idClient, "date" to fecha, "time" to hora, "asesoria_id" to idAsesoria)
        supabase.from("citas")
            .insert(citaInfo)
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

    suspend fun setAsesoria(
        description: String,
        category: String
    ) {
        val idClient = supabase.auth.retrieveUserForCurrentSession().id
        val setAsesoria = mapOf(
            "cliente_id" to idClient,
            "description" to description,
            "category" to category,
            "status" to "pendiente"
        )
        supabase.from("asesorias")
            .insert(setAsesoria)
    }

    suspend fun updateAsesoria(updates: Map<String, String>) {
        val userId = supabase.auth.retrieveUserForCurrentSession().id
        supabase.from("perfil")
            .update(updates) {
                filter {
                    eq("id", userId)
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

