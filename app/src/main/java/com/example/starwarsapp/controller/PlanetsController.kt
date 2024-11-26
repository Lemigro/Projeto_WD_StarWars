package com.example.starwarsapp.controller

import com.example.starwarsapp.model.PlanetsModel
import com.example.starwarsapp.repository.PlanetsRepository

class PlanetsController(private val planetsRepository: PlanetsRepository) {

    fun fetchPlanets(page: Int, onSuccess: (List<PlanetsModel>) -> Unit, onError: (Throwable) -> Unit) {
        planetsRepository.getPlanets(
            page,
            onSuccess = { planets ->
                onSuccess(planets)
            },
            onError = { throwable ->
                onError(throwable)
            }
        )
    }

    fun getPlanetDetails(id: String, onSuccess: (PlanetsModel) -> Unit, onError: (Throwable) -> Unit) {
        planetsRepository.getPlanetById(id, onSuccess, onError)
    }
}