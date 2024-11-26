package com.example.starwarsapp.repository

import com.example.starwarsapp.model.StarshipsModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StarshipsRepository {

    fun getStarships(page: Int, onSuccess: (List<StarshipsModel>) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getStarships(page)

        call.enqueue(object : Callback<ApiResponse<StarshipsModel>> {
            override fun onResponse(call: Call<ApiResponse<StarshipsModel>>, response: Response<ApiResponse<StarshipsModel>>) {
                if (response.isSuccessful) {
                    val starships = response.body()?.results
                    if (!starships.isNullOrEmpty()) {
                        onSuccess(starships)
                    } else {
                        onError(Throwable("A resposta das naves está vazia ou nula"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar as naves: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<StarshipsModel>>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }

    fun getStarshipById(id: String, onSuccess: (StarshipsModel) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getStarshipsById(id)

        call.enqueue(object : Callback<StarshipsModel> {
            override fun onResponse(call: Call<StarshipsModel>, response: Response<StarshipsModel>) {
                if (response.isSuccessful) {
                    val starship = response.body()
                    if (starship != null) {
                        onSuccess(starship)
                    } else {
                        onError(Throwable("A resposta da nave está vazia"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar detalhes da nave: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<StarshipsModel>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }
}
