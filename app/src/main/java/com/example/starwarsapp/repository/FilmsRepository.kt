package com.example.starwarsapp.repository

import com.example.starwarsapp.model.FilmsModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsRepository {

    fun getFilms(page: Int, onSuccess: (List<FilmsModel>) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getFilms(page)

        call.enqueue(object : Callback<ApiResponse<FilmsModel>> {
            override fun onResponse(call: Call<ApiResponse<FilmsModel>>, response: Response<ApiResponse<FilmsModel>>) {
                if (response.isSuccessful) {
                    val films = response.body()?.results
                    if (!films.isNullOrEmpty()) {
                        onSuccess(films)
                    } else {
                        onError(Throwable("A resposta dos filmes está vazia ou nula"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar os filmes: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<FilmsModel>>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }

    fun getFilmById(id: String, onSuccess: (FilmsModel) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getFilmsById(id)

        call.enqueue(object : Callback<FilmsModel> {
            override fun onResponse(call: Call<FilmsModel>, response: Response<FilmsModel>) {
                if (response.isSuccessful) {
                    val film = response.body()
                    if (film != null) {
                        onSuccess(film)
                    } else {
                        onError(Throwable("A resposta do filme está vazia"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar detalhes do filme: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<FilmsModel>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }
}
