package com.example.starwarsapp.model

data class FavoritesModel(
    val itemId: String,
    val itemType: String,
    val title: String = "",
    val description: String = "",
    val imageUrl: String = ""
) {
    constructor() : this("", "", "", "", "")
}
