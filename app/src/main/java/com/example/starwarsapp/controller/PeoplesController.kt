package com.example.starwarsapp.controller

import com.example.starwarsapp.model.PeoplesModel
import com.example.starwarsapp.repository.PeoplesRepository
import android.util.Log

class PeoplesController(private val peoplesRepository: PeoplesRepository) {

    suspend fun fetchPeople(page: Int, onSuccess: (List<PeoplesModel>) -> Unit, onError: (Throwable) -> Unit) {
        Log.d("PeoplesController", "Fetching people for page: $page")
        println("Entrei no FetchPeople no PeopleCOntroller")
        val result = peoplesRepository.getPeople(page)
        result.fold(onSuccess, onError)
    }

    suspend fun fetchPeopleDetails(id: String, onSuccess: (PeoplesModel) -> Unit, onError: (Throwable) -> Unit) {
        Log.d("PeoplesController", "Fetching people details for ID: $id")
        val result = peoplesRepository.getPeopleById(id)
        result.fold(onSuccess, onError)
    }
}
