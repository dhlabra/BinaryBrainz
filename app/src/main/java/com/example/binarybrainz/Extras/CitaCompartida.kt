package com.example.binarybrainz.Extras

import kotlinx.serialization.Serializable

@Serializable
data class CitaCompartida(
    val id: String,
    val asesoria_id: Int,
    var estudiante_id: String
)