package com.kubernights.tp3.parcialnw.data.network

import com.kubernights.tp3.parcialnw.data.model.BreedsApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BreedService @Inject constructor(private val apiClient: BreedApiClient) {

    suspend fun getBreeds(): BreedsApiResponse {
        // Moved to IO Dispatcher to make network request off the main thread
        return withContext(Dispatchers.IO) {
            val response = apiClient.getAllBreeds()
            response.body() ?: throw Exception("Failed to fetch breeds data")
        }
    }
}