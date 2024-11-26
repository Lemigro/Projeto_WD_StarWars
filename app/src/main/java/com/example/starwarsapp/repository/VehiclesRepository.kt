package com.example.starwarsapp.repository

import com.example.starwarsapp.model.VehiclesModel
import com.example.starwarsapp.network.ApiClient
import com.example.starwarsapp.network.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VehiclesRepository {

    fun getVehicles(page: Int, onSuccess: (List<VehiclesModel>) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getVehicles(page)

        call.enqueue(object : Callback<ApiResponse<VehiclesModel>> {
            override fun onResponse(call: Call<ApiResponse<VehiclesModel>>, response: Response<ApiResponse<VehiclesModel>>) {
                if (response.isSuccessful) {
                    val vehicles = response.body()?.results
                    if (!vehicles.isNullOrEmpty()) {
                        onSuccess(vehicles)
                    } else {
                        onError(Throwable("A resposta dos veículos está vazia ou nula"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar os veículos: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ApiResponse<VehiclesModel>>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }

    fun getVehicleById(id: String, onSuccess: (VehiclesModel) -> Unit, onError: (Throwable) -> Unit) {
        val call = ApiClient.apiService.getVehiclesById(id)

        call.enqueue(object : Callback<VehiclesModel> {
            override fun onResponse(call: Call<VehiclesModel>, response: Response<VehiclesModel>) {
                if (response.isSuccessful) {
                    val vehicle = response.body()
                    if (vehicle != null) {
                        onSuccess(vehicle)
                    } else {
                        onError(Throwable("A resposta do veículo está vazia"))
                    }
                } else {
                    onError(Throwable("Falha ao buscar detalhes do veículo: Código ${response.code()}, ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<VehiclesModel>, t: Throwable) {
                onError(Throwable("Erro de rede: ${t.message}"))
            }
        })
    }
}
