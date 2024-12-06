package com.example.starwarsapp.controller

import com.example.starwarsapp.model.SpeciesModel
import com.example.starwarsapp.repository.SpeciesRepository

class SpeciesController(private val speciesRepository: SpeciesRepository) {

    suspend fun fetchSpecies(
        page: Int,
        onSuccess: (List<SpeciesModel>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = speciesRepository.getSpecies(page)
        result.onSuccess(onSuccess).onFailure(onError)
    }

    suspend fun getSpeciesDetails(
        id: String,
        onSuccess: (SpeciesModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = speciesRepository.getSpeciesById(id)
        result.onSuccess(onSuccess).onFailure(onError)
    }
}
