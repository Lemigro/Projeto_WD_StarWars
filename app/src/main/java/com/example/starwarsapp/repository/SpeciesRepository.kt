package com.example.starwarsapp.repository

import com.example.starwarsapp.model.SpeciesModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpeciesRepository {

    fun getSpecies(page: Int, onSuccess: (List<SpeciesModel>) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getSpecies(page)

        call.enqueue(object : Callback<ApiResponse<SpeciesModel>> {
            override fun onResponse(call: Call<ApiResponse<SpeciesModel>>, response: Response<ApiResponse<SpeciesModel>>) {
                if (response.isSuccessful) {
                    val speciesList = response.body()?.results
                    if (!speciesList.isNullOrEmpty()) {
                        onSuccess(speciesList)
                    } else {
                        onError(Throwable("A resposta das espécies está vazia ou nula"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar as espécies: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<SpeciesModel>>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }

    fun getSpeciesById(id: String, onSuccess: (SpeciesModel) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getSpeciesById(id)

        call.enqueue(object : Callback<SpeciesModel> {
            override fun onResponse(call: Call<SpeciesModel>, response: Response<SpeciesModel>) {
                if (response.isSuccessful) {
                    val species = response.body()
                    if (species != null) {
                        onSuccess(species)
                    } else {
                        onError(Throwable("A resposta da espécie está vazia"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar detalhes da espécie: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<SpeciesModel>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }
}
