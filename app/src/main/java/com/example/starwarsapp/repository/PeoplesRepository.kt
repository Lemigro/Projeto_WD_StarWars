package com.example.starwarsapp.repository

import com.example.starwarsapp.model.PeoplesModel
import com.example.starwarsapp.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class PeoplesRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T?): Result<T> {
        Log.d("PeoplesRepository", "Entrando na safeApiCall")
        return withContext(Dispatchers.IO) {
            try {
                val result = apiCall()
                if (result != null) {
                    Result.success(result)
                } else {
                    Result.failure(Throwable("Resposta vazia"))
                }
            } catch (e: Exception) {
                Result.failure(Throwable("Erro de rede: ${e.message}", e))
            }
        }
    }

    suspend fun getPeople(page: Int): Result<List<PeoplesModel>> {
        Log.d("PeoplesRepository", "Buscando pessoas para a página: $page")
        return safeApiCall {
            val response = ApiClient.apiService.getPeoples(page)
            if (response.isSuccessful) {
                response.body()?.results
            } else {
                throw Throwable("Falha ao buscar personagens: Código ${response.code()}, ${response.message()}")
            }
        }
    }

    suspend fun getPeopleById(id: String): Result<PeoplesModel> {
        Log.d("PeoplesRepository", "Buscando detalhes de pessoas para o ID: $id")
        return safeApiCall {
            val response = ApiClient.apiService.getPeoplesById(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                throw Throwable("Falha ao buscar detalhes da pessoa: Código ${response.code()}, ${response.message()}")
            }
        }
    }
}
