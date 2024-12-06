package com.example.starwarsapp.controller

import com.example.starwarsapp.model.FavoritesModel
import com.example.starwarsapp.repository.FavoritesRepository

class FavoritesController(private val repository: FavoritesRepository) {

    fun saveFavorites(userId: String, favorite: FavoritesModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        repository.saveFavorites(userId, favorite, onSuccess, onFailure)
    }

    fun loadFavorites(userId: String, onSuccess: (List<FavoritesModel>) -> Unit, onFailure: (String) -> Unit) {
        repository.loadFavorites(userId, onSuccess, onFailure)
    }

    fun removeFavorites(userId: String, favorite: FavoritesModel, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        repository.removeFavorite(userId, favorite, onSuccess, onFailure)
    }
}
