package com.example.starwarsapp.controller

import com.example.starwarsapp.model.FilmsModel
import com.example.starwarsapp.repository.FilmsRepository

class FilmsController(private val filmsRepository: FilmsRepository) {

    fun fetchFilms(page: Int, onSuccess: (List<FilmsModel>) -> Unit, onError: (Throwable) -> Unit) {
        filmsRepository.getFilms(
            page,
            onSuccess = { films ->
                onSuccess(films)
            },
            onError = { throwable ->
                onError(throwable)
            }
        )
    }

    fun getFilmDetails(id: String, onSuccess: (FilmsModel) -> Unit, onError: (Throwable) -> Unit) {
        filmsRepository.getFilmById(id, onSuccess, onError)
    }
}
