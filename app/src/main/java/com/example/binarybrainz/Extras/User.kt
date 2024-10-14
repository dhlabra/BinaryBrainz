package com.example.binarybrainz.Extras

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    var role: String,
    val nombre: String,
    val apellido: String,
    val celular: String
)