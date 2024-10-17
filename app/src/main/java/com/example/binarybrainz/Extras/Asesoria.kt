package com.example.binarybrainz.Extras

import kotlinx.serialization.Serializable

@Serializable
data class Asesoria(
    val id: Int,
    var description: String,
    var category: String,
    var cliente_id: String,
    val created_at: String,
    var status: String,
    var gravedad: Float
)