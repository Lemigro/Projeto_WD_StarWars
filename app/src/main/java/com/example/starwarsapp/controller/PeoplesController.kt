package com.example.starwarsapp.controller

import com.example.starwarsapp.model.PeoplesModel
import com.example.starwarsapp.repository.PeoplesRepository

class PeoplesController(private val peoplesRepository: PeoplesRepository) {

    fun fetchPeople(page: Int, onSuccess: (List<PeoplesModel>) -> Unit, onError: (Throwable) -> Unit) {
        peoplesRepository.getPeople(
            page,
            onSuccess = { people ->
                onSuccess(people)
            },
            onError = { throwable ->
                onError(throwable)
            }
        )
    }

    fun getPeopleDetails(id: String, onSuccess: (PeoplesModel) -> Unit, onError: (Throwable) -> Unit) {
        peoplesRepository.getPeopleById(id, onSuccess, onError)
    }
}
