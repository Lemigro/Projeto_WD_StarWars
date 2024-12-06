package com.example.starwarsapp.repository

import com.example.starwarsapp.model.PlanetsModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse

class PlanetsRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Result.failure(Throwable("Erro de rede: ${e.message}", e))
        }
    }

    suspend fun getPlanets(page: Int): Result<List<PlanetsModel>> {
        return safeApiCall {
            val response = ApiClient.apiService.getPlanets(page)
            if (response.isSuccessful) {
                val planets = response.body()?.results
                if (!planets.isNullOrEmpty()) {
                    planets
                } else {
                    throw Throwable("A resposta dos planetas está vazia ou nula")
                }
            } else {
                throw Throwable("Falha ao buscar os planetas: Código ${response.code()}, ${response.message()}")
            }
        }
    }

    suspend fun getPlanetById(id: String): Result<PlanetsModel> {
        return safeApiCall {
            val response = ApiClient.apiService.getPlanetsById(id)
            if (response.isSuccessful) {
                response.body() ?: throw Throwable("A resposta do planeta está vazia")
            } else {
                throw Throwable("Falha ao buscar detalhes do planeta: Código ${response.code()}, ${response.message()}")
            }
        }
    }
}
