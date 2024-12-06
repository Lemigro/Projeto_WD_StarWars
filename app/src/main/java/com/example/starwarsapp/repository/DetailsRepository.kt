package com.example.starwarsapp.repository

import android.util.Log
import com.example.starwarsapp.model.DetailsModel
import com.example.starwarsapp.network.ApiClient

class DetailsRepository {

    suspend fun fetchDetails(itemId: String, itemType: String): DetailsModel? {
        return when (itemType) {
            "people" -> {
                println("Entrei no FetchDetails de People no DetailsRepository")
                val response = ApiClient.apiService.getPeoplesById(itemId)
                Log.d("DetailsRepository", "Chamando API para $itemType com ID: $itemId")
                if (response.isSuccessful) {
                    response.body()?.let { person ->
                        DetailsModel(
                            name = person.name,
                            description = " Altura: ${person.height}, Peso: ${person.mass}, Gênero: ${person.gender}",
                            imageUrl = "https://starwars-visualguide.com/assets/img/characters/$itemId.jpg",
                            additionalData = mapOf(
                                "Cor do cabelo" to person.hairColor,
                                "Cor da pele" to person.skinColor,
                                "Cor dos olhos" to person.eyeColor,
                                "Ano de nascimento" to person.birthYear
                            )
                        )
                    }
                } else null
            }
            "films" -> {
                println("Entrei no FetchDetails de Films no DetailsRepository")
                val response = ApiClient.apiService.getFilmsById(itemId)
                Log.d("DetailsRepository", "Chamando API para $itemType com ID: $itemId")
                if (response.isSuccessful) {
                    response.body()?.let { film ->
                        DetailsModel(
                            name = film.title,
                            description = " Episódio ${film.episodeId}, Diretor: ${film.director}",
                            imageUrl = "https://starwars-visualguide.com/assets/img/films/$itemId.jpg",
                            additionalData = mapOf(
                                "Sinopse" to film.openingCrawl,
                                "Produtor" to film.producer,
                                "Data de lançamento" to film.releaseDate
                            )
                        )
                    }
                } else null
            }
            "planets" -> {
                println("Entrei no FetchDetails de Planets no DetailsRepository")
                val response = ApiClient.apiService.getPlanetsById(itemId)
                Log.d("DetailsRepository", "Chamando API para $itemType com ID: $itemId")
                if (response.isSuccessful) {
                    response.body()?.let { planet ->
                        DetailsModel(
                            name = planet.name,
                            description = " Clima: ${planet.climate}, Gravidade: ${planet.gravity}",
                            imageUrl = "https://starwars-visualguide.com/assets/img/planets/$itemId.jpg",
                            additionalData = mapOf(
                                "Terreno" to planet.terrain,
                                "População" to planet.population,
                                "Água na superfície" to planet.surfaceWater
                            )
                        )
                    }
                } else null
            }
            "vehicles" -> {
                println("Entrei no FetchDetails de Vehicles no DetailsRepository")
                val response = ApiClient.apiService.getVehiclesById(itemId)
                Log.d("DetailsRepository", "Chamando API para $itemType com ID: $itemId")
                if (response.isSuccessful) {
                    response.body()?.let { vehicle ->
                        DetailsModel(
                            name = vehicle.name,
                            description = " Modelo: ${vehicle.model}, Classe: ${vehicle.vehicleClass}",
                            imageUrl = "https://starwars-visualguide.com/assets/img/vehicles/$itemId.jpg",
                            additionalData = mapOf(
                                "Fabricante" to vehicle.manufacturer,
                                "Custo" to vehicle.costInCredits,
                                "Capacidade de carga" to vehicle.cargoCapacity,
                                "Velocidade atmosférica máxima" to vehicle.maxAtmospheringSpeed
                            )
                        )
                    }
                } else null
            }
            "species" -> {
                println("Entrei no FetchDetails de Species no DetailsRepository")
                val response = ApiClient.apiService.getSpeciesById(itemId)
                Log.d("DetailsRepository", "Chamando API para $itemType com ID: $itemId")
                if (response.isSuccessful) {
                    response.body()?.let { species ->
                        DetailsModel(
                            name = species.name,
                            description = " Classificação: ${species.classification}, Idioma: ${species.language}",
                            imageUrl = "https://starwars-visualguide.com/assets/img/species/$itemId.jpg",
                            additionalData = mapOf(
                                "Altura média" to species.averageHeight,
                                "Cores de pele" to species.skinColors,
                                "Cores de cabelo" to species.hairColors,
                                "Expectativa de vida" to species.averageLifespan
                            )
                        )
                    }
                } else null
            }
            "starships" -> {
                println("Entrei no FetchDetails de Starships no DetailsRepository")
                val response = ApiClient.apiService.getStarshipsById(itemId)
                Log.d("DetailsRepository", "Chamando API para $itemType com ID: $itemId")
                if (response.isSuccessful) {
                    response.body()?.let { starship ->
                        DetailsModel(
                            name = starship.name,
                            description = " Modelo: ${starship.model}, Classe: ${starship.starshipClass}",
                            imageUrl = "https://starwars-visualguide.com/assets/img/starships/$itemId.jpg",
                            additionalData = mapOf(
                                "Fabricante" to starship.manufacturer,
                                "Custo" to starship.costInCredits,
                                "Velocidade atmosférica máxima" to starship.maxAtmospheringSpeed
                            )
                        )
                    }
                } else null
            }
            else -> null
        }
    }
}
