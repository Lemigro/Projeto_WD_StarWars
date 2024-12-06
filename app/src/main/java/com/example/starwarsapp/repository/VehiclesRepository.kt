package com.example.starwarsapp.repository

import com.example.starwarsapp.model.VehiclesModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse

class VehiclesRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Result.failure(Throwable("Erro de rede: ${e.message}", e))
        }
    }

    suspend fun getVehicles(page: Int): Result<List<VehiclesModel>> {
        return safeApiCall {
            val response = ApiClient.apiService.getVehicles(page)
            if (response.isSuccessful) {
                val vehicles = response.body()?.results
                if (!vehicles.isNullOrEmpty()) {
                    vehicles
                } else {
                    throw Throwable("A resposta dos veículos está vazia ou nula")
                }
            } else {
                throw Throwable("Falha ao buscar os veículos: Código ${response.code()}, ${response.message()}")
            }
        }
    }

    suspend fun getVehicleById(id: String): Result<VehiclesModel> {
        return safeApiCall {
            val response = ApiClient.apiService.getVehiclesById(id)
            if (response.isSuccessful) {
                response.body() ?: throw Throwable("A resposta do veículo está vazia")
            } else {
                throw Throwable("Falha ao buscar detalhes do veículo: Código ${response.code()}, ${response.message()}")
            }
        }
    }
}
