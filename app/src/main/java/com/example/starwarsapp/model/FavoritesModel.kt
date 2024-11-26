package com.example.starwarsapp.model

data class FavoritesModel(
    val itemId: String,
    val itemType: String,
    val title: String = "",
    val imageResId: Int = 0,
    val description: String = ""
)
