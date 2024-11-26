package com.example.starwarsapp.controller

import com.example.starwarsapp.model.VehiclesModel
import com.example.starwarsapp.repository.VehiclesRepository

class VehiclesController(private val vehiclesRepository: VehiclesRepository) {

    fun fetchVehicles(page: Int, onSuccess: (List<VehiclesModel>) -> Unit, onError: (Throwable) -> Unit) {
        vehiclesRepository.getVehicles(
            page,
            onSuccess = { vehicles ->
                onSuccess(vehicles)
            },
            onError = { throwable ->
                onError(throwable)
            }
        )
    }

    fun getVehicleDetails(id: String, onSuccess: (VehiclesModel) -> Unit, onError: (Throwable) -> Unit) {
        vehiclesRepository.getVehicleById(id, onSuccess, onError)
    }
}
