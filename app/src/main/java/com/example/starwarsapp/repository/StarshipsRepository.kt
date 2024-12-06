package com.example.starwarsapp.repository

import com.example.starwarsapp.model.StarshipsModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse

class StarshipsRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Result.failure(Throwable("Erro de rede: ${e.message}", e))
        }
    }

    suspend fun getStarships(page: Int): Result<List<StarshipsModel>> {
        return safeApiCall {
            val response = ApiClient.apiService.getStarships(page)
            if (response.isSuccessful) {
                val starships = response.body()?.results
                if (!starships.isNullOrEmpty()) {
                    starships
                } else {
                    throw Throwable("A lista de naves está vazia")
                }
            } else {
                throw Throwable("Falha ao buscar as naves: Código ${response.code()}, ${response.message()}")
            }
        }
    }

    suspend fun getStarshipById(id: String): Result<StarshipsModel> {
        return safeApiCall {
            val response = ApiClient.apiService.getStarshipsById(id)
            if (response.isSuccessful) {
                response.body() ?: throw Throwable("A resposta da nave está vazia")
            } else {
                throw Throwable("Falha ao buscar detalhes da nave: Código ${response.code()}, ${response.message()}")
            }
        }
    }
}
