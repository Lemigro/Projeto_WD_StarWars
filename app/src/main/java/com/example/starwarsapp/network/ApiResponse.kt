package com.example.starwarsapp.network

data class ApiResponse<T>(
    val results: List<T>
)