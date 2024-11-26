package com.example.starwarsapp.repository

import com.example.starwarsapp.model.PeoplesModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeoplesRepository {

    fun getPeople(page: Int, onSuccess: (List<PeoplesModel>) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getPeoples(page)

        call.enqueue(object : Callback<ApiResponse<PeoplesModel>> {
            override fun onResponse(call: Call<ApiResponse<PeoplesModel>>, response: Response<ApiResponse<PeoplesModel>>) {
                if (response.isSuccessful) {
                    val peopleList = response.body()?.results
                    if (!peopleList.isNullOrEmpty()) {
                        onSuccess(peopleList)
                    } else {
                        onError(Throwable("A resposta dos personagens está vazia ou nula"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar os personagens: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<PeoplesModel>>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }

    fun getPeopleById(id: String, onSuccess: (PeoplesModel) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getPeoplesById(id)

        call.enqueue(object : Callback<PeoplesModel> {
            override fun onResponse(call: Call<PeoplesModel>, response: Response<PeoplesModel>) {
                if (response.isSuccessful) {
                    val person = response.body()
                    if (person != null) {
                        onSuccess(person)
                    } else {
                        onError(Throwable("Os detalhes da pessoa estão vazios"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar detalhes da pessoa: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<PeoplesModel>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }
}
