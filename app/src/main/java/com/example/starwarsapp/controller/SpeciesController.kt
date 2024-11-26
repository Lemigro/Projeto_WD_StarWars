package com.example.starwarsapp.controller

import com.example.starwarsapp.model.SpeciesModel
import com.example.starwarsapp.repository.SpeciesRepository

class SpeciesController(private val speciesRepository: SpeciesRepository) {

    fun fetchSpecies(page: Int, onSuccess: (List<SpeciesModel>) -> Unit, onError: (Throwable) -> Unit) {
        speciesRepository.getSpecies(
            page,
            onSuccess = { species ->
                onSuccess(species)
            },
            onError = { throwable ->
                onError(throwable)
            }
        )
    }

    fun getSpeciesDetails(id: String, onSuccess: (SpeciesModel) -> Unit, onError: (Throwable) -> Unit) {
        speciesRepository.getSpeciesById(id, onSuccess, onError)
    }
}
