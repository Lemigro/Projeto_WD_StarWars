package com.example.starwarsapp.controller

import com.example.starwarsapp.model.StarshipsModel
import com.example.starwarsapp.repository.StarshipsRepository

class StarshipsController(private val starshipsRepository: StarshipsRepository) {

    suspend fun fetchStarships(
        page: Int,
        onSuccess: (List<StarshipsModel>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = starshipsRepository.getStarships(page)
        result.onSuccess(onSuccess).onFailure(onError)
    }

    suspend fun getStarshipDetails(
        id: String,
        onSuccess: (StarshipsModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = starshipsRepository.getStarshipById(id)
        result.onSuccess(onSuccess).onFailure(onError)
    }
}
