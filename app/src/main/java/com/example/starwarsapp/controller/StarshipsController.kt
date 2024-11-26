package com.example.starwarsapp.controller

import com.example.starwarsapp.model.StarshipsModel
import com.example.starwarsapp.repository.StarshipsRepository

class StarshipsController(private val starshipRepository: StarshipsRepository) {

    fun fetchStarships(page: Int, onSuccess: (List<StarshipsModel>) -> Unit, onError: (Throwable) -> Unit) {
        starshipRepository.getStarships(
            page,
            onSuccess = { starships ->
                onSuccess(starships)
            },
            onError = { throwable ->
                onError(throwable)
            }
        )
    }

    fun getStarshipDetails(id: String, onSuccess: (StarshipsModel) -> Unit, onError: (Throwable) -> Unit) {
        starshipRepository.getStarshipById(id, onSuccess, onError)
    }
}
