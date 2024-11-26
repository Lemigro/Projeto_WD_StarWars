package com.example.starwarsapp.repository

import com.example.starwarsapp.model.PlanetsModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlanetsRepository {

    fun getPlanets(page: Int, onSuccess: (List<PlanetsModel>) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getPlanets(page)

        call.enqueue(object : Callback<ApiResponse<PlanetsModel>> {
            override fun onResponse(call: Call<ApiResponse<PlanetsModel>>, response: Response<ApiResponse<PlanetsModel>>) {
                if (response.isSuccessful) {
                    val planets = response.body()?.results
                    if (!planets.isNullOrEmpty()) {
                        onSuccess(planets)
                    } else {
                        onError(Throwable("A resposta dos planetas está vazia ou nula"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar os planetas: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<PlanetsModel>>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }

    fun getPlanetById(id: String, onSuccess: (PlanetsModel) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getPlanetsById(id)

        call.enqueue(object : Callback<PlanetsModel> {
            override fun onResponse(call: Call<PlanetsModel>, response: Response<PlanetsModel>) {
                if (response.isSuccessful) {
                    val planet = response.body()
                    if (planet != null) {
                        onSuccess(planet)
                    } else {
                        onError(Throwable("A resposta do planeta está vazia"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar detalhes do planeta: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<PlanetsModel>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }
}
