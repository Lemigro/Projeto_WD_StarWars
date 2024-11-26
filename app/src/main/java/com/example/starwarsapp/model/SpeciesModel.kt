package com.example.starwarsapp.model

data class SpeciesModel(
    val name: String,
    val classification: String,
    val designation: String,
    val averageHeight: String,
    val skinColors: String,
    val hairColors: String,
    val eyeColors: String,
    val averageLifespan: String,
    val homeworld: String,
    val language: String,
    val people: List<String>,
    val films: List<String>,
    val url: String
)
