package com.example.starwarsapp.controller

import com.example.starwarsapp.model.DetailsModel
import com.example.starwarsapp.repository.DetailsRepository
import android.util.Log

class DetailsController(private val repository: DetailsRepository) {

    suspend fun fetchDetails(
        itemId: String,
        itemType: String,
        onSuccess: (DetailsModel) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            Log.d("DetailsController", "Buscando detalhes para o $itemType com ID: $itemId")
            println("Entrei no FetchDetails no DetailsController")
            val details = repository.fetchDetails(itemId, itemType)
            if (details != null) {
                Log.d("DetailsController", "Detalhes obtidos com sucesso: $details")
                onSuccess(details)
            } else {
                Log.e("DetailsController", "Detalhes não encontrados para esse ID: $itemId")
                onError(Throwable("Detalhes não encontrados"))
            }
        } catch (e: Exception) {
            Log.e("DetailsController", "Erro ao buscar detalhes", e)
            onError(e)
        }
    }

}
