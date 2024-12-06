package com.example.starwarsapp.repository

import com.example.starwarsapp.model.SpeciesModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse

class SpeciesRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Result.failure(Throwable("Erro de rede: ${e.message}", e))
        }
    }

    suspend fun getSpecies(page: Int): Result<List<SpeciesModel>> {
        return safeApiCall {
            val response = ApiClient.apiService.getSpecies(page)
            if (response.isSuccessful) {
                val speciesList = response.body()?.results
                if (!speciesList.isNullOrEmpty()) {
                    speciesList
                } else {
                    throw Throwable("A resposta das espécies está vazia ou nula")
                }
            } else {
                throw Throwable("Falha ao buscar as espécies: Código ${response.code()}, ${response.message()}")
            }
        }
    }

    suspend fun getSpeciesById(id: String): Result<SpeciesModel> {
        return safeApiCall {
            val response = ApiClient.apiService.getSpeciesById(id)
            if (response.isSuccessful) {
                response.body() ?: throw Throwable("A resposta da espécie está vazia")
            } else {
                throw Throwable("Falha ao buscar detalhes da espécie: Código ${response.code()}, ${response.message()}")
            }
        }
    }
}
