package com.example.starwarsapp.controller

import com.example.starwarsapp.model.VehiclesModel
import com.example.starwarsapp.repository.VehiclesRepository

class VehiclesController(private val vehiclesRepository: VehiclesRepository) {

    suspend fun fetchVehicles(
        page: Int,
        onSuccess: (List<VehiclesModel>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = vehiclesRepository.getVehicles(page)
        result.onSuccess(onSuccess).onFailure(onError)
    }

    suspend fun getVehicleDetails(
        id: String,
        onSuccess: (VehiclesModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val result = vehiclesRepository.getVehicleById(id)
        result.onSuccess(onSuccess).onFailure(onError)
    }
}
