package com.example.starwarsapp.controller

import com.example.starwarsapp.model.PlanetsModel
import com.example.starwarsapp.repository.PlanetsRepository

class PlanetsController(private val planetsRepository: PlanetsRepository) {

    suspend fun fetchPlanets(
        page: Int,
        onSuccess: (List<PlanetsModel>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = planetsRepository.getPlanets(page)
        result.onSuccess(onSuccess).onFailure(onError)
    }

    suspend fun getPlanetDetails(
        id: String,
        onSuccess: (PlanetsModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = planetsRepository.getPlanetById(id)
        result.onSuccess(onSuccess).onFailure(onError)
    }
}
