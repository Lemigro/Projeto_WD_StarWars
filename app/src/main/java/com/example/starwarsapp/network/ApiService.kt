package com.example.starwarsapp.network

import com.example.starwarsapp.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    fun getPeoples(@Query("page") page: Int): Call<ApiResponse<PeoplesModel>>

    @GET("people/{id}/")
    fun getPeoplesById(@Path("id") id: String): Call<PeoplesModel>

    @GET("planets/")
    fun getPlanets(@Query("page") page: Int): Call<ApiResponse<PlanetsModel>>

    @GET("planets/{id}/")
    fun getPlanetsById(@Path("id") id: String): Call<PlanetsModel>

    @GET("vehicles/")
    fun getVehicles(@Query("page") page: Int): Call<ApiResponse<VehiclesModel>>

    @GET("vehicles/{id}/")
    fun getVehiclesById(@Path("id") id: String): Call<VehiclesModel>

    @GET("films/")
    fun getFilms(@Query("page") page: Int): Call<ApiResponse<FilmsModel>>

    @GET("films/{id}/")
    fun getFilmsById(@Path("id") id: String): Call<FilmsModel>

    @GET("species/")
    fun getSpecies(@Query("page") page: Int): Call<ApiResponse<SpeciesModel>>

    @GET("species/{id}/")
    fun getSpeciesById(@Path("id") id: String): Call<SpeciesModel>

    @GET("starships/")
    fun getStarships(@Query("page") page: Int): Call<ApiResponse<StarshipsModel>>

    @GET("starships/{id}/")
    fun getStarshipsById(@Path("id") id: String): Call<StarshipsModel>
}
