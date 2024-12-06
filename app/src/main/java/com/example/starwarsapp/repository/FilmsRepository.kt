package com.example.starwarsapp.repository

import com.example.starwarsapp.model.FilmsModel
import com.example.starwarsapp.network.ApiClient

class FilmsRepository {

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Result.success(apiCall())
        } catch (e: Exception) {
            Result.failure(Throwable("Erro de rede: ${e.message}", e))
        }
    }

    suspend fun getFilms(page: Int): Result<List<FilmsModel>> {
        return safeApiCall {
            val response = ApiClient.apiService.getFilms(page)
            if (response.isSuccessful) {
                val films = response.body()?.results
                if (!films.isNullOrEmpty()) {
                    films
                } else {
                    throw Throwable("A resposta dos filmes está vazia ou nula")
                }
            } else {
                throw Throwable("Falha ao buscar os filmes: Código ${response.code()}, ${response.message()}")
            }
        }
    }

    suspend fun getFilmById(id: String): Result<FilmsModel> {
        return safeApiCall {
            val response = ApiClient.apiService.getFilmsById(id)
            if (response.isSuccessful) {
                response.body() ?: throw Throwable("A resposta do filme está vazia")
            } else {
                throw Throwable("Falha ao buscar detalhes do filme: Código ${response.code()}, ${response.message()}")
            }
        }
    }
}
