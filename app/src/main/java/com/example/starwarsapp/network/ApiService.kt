package com.example.starwarsapp.network

import com.example.starwarsapp.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("people/")
    suspend fun getPeoples(@Query("page") page: Int): Response<ApiResponse<PeoplesModel>>

    @GET("people/{id}/")
    suspend fun getPeoplesById(@Path("id") id: String): Response<PeoplesModel>

    @GET("planets/")
    suspend fun getPlanets(@Query("page") page: Int): Response<ApiResponse<PlanetsModel>>

    @GET("planets/{id}/")
    suspend fun getPlanetsById(@Path("id") id: String): Response<PlanetsModel>

    @GET("vehicles/")
    suspend fun getVehicles(@Query("page") page: Int): Response<ApiResponse<VehiclesModel>>

    @GET("vehicles/{id}/")
    suspend fun getVehiclesById(@Path("id") id: String): Response<VehiclesModel>

    @GET("films/")
    suspend fun getFilms(@Query("page") page: Int): Response<ApiResponse<FilmsModel>>

    @GET("films/{id}/")
    suspend fun getFilmsById(@Path("id") id: String): Response<FilmsModel>

    @GET("species/")
    suspend fun getSpecies(@Query("page") page: Int): Response<ApiResponse<SpeciesModel>>

    @GET("species/{id}/")
    suspend fun getSpeciesById(@Path("id") id: String): Response<SpeciesModel>

    @GET("starships/")
    suspend fun getStarships(@Query("page") page: Int): Response<ApiResponse<StarshipsModel>>

    @GET("starships/{id}/")
    suspend fun getStarshipsById(@Path("id") id: String): Response<StarshipsModel>
}
