package com.example.binarybrainz.Extras

import kotlinx.serialization.Serializable

@Serializable
data class Asesoria(
    val id: String,
    var title: String,
    var description: String,
    var category: String,
    val created_at: String,
    var status: String
)