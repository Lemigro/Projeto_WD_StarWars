package com.example.starwarsapp.model

import com.google.gson.annotations.SerializedName

data class FilmsModel(
    @SerializedName("title") val title: String?,
    @SerializedName("episode_id") val episodeId: Int?,
    @SerializedName("opening_crawl") val openingCrawl: String?,
    @SerializedName("director") val director: String?,
    @SerializedName("producer") val producer: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("characters") val characters: List<String>?,
    @SerializedName("planets") val planets: List<String>?,
    @SerializedName("starships") val starships: List<String>?,
    @SerializedName("vehicles") val vehicles: List<String>?,
    @SerializedName("species") val species: List<String>?,
    @SerializedName("url") val url: String?
) {
    val id: String?
        get() = url?.split("/")?.filter { it.isNotEmpty() }?.lastOrNull()
}

