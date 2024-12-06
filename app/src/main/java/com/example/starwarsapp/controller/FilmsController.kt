package com.example.starwarsapp.controller

import com.example.starwarsapp.model.FilmsModel
import com.example.starwarsapp.repository.FilmsRepository

class FilmsController(private val filmsRepository: FilmsRepository) {

    suspend fun fetchFilms(
        page: Int,
        onSuccess: (List<FilmsModel>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = filmsRepository.getFilms(page)
        result.onSuccess(onSuccess).onFailure(onError)
    }

    suspend fun getFilmDetails(
        id: String,
        onSuccess: (FilmsModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = filmsRepository.getFilmById(id)
        result.onSuccess(onSuccess).onFailure(onError)
    }
}
