package com.example.binarybrainz.Extras

import kotlinx.serialization.Serializable

@Serializable
data class Cita(
    val id: String,
    val client_id: String,
    var client_phone: String,
    val asesoria_id: String,
    var date: String,
    var time_slot: String,
    var status: String
)