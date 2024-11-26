package com.example.starwarsapp.model

import com.google.gson.annotations.SerializedName

data class VehiclesModel(
    @SerializedName("name") val name: String,
    @SerializedName("model") val model: String,
    @SerializedName("manufacturer") val manufacturer: String,
    @SerializedName("cost_in_credits") val costInCredits: String,
    @SerializedName("length") val length: String,
    @SerializedName("max_atmosphering_speed") val maxAtmospheringSpeed: String,
    @SerializedName("crew") val crew: String,
    @SerializedName("passengers") val passengers: String,
    @SerializedName("cargo_capacity") val cargoCapacity: String,
    @SerializedName("consumables") val consumables: String,
    @SerializedName("vehicle_class") val vehicleClass: String,
    @SerializedName("pilots") val pilots: List<String>,
    @SerializedName("films") val films: List<String>,
    @SerializedName("url") val url: String
)

