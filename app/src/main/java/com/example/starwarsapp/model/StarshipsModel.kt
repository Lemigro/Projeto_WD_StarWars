package com.example.starwarsapp.model

data class StarshipsModel(
    val name: String,
    val model: String,
    val manufacturer: String,
    val costInCredits: String,
    val length: String,
    val maxAtmospheringSpeed: String,
    val crew: String,
    val passengers: String,
    val cargoCapacity: String,
    val consumables: String,
    val hyperdriveRating: String,
    val MGLT: String,
    val starshipClass: String,
    val pilots: List<String>,
    val films: List<String>,
    val url: String
)
