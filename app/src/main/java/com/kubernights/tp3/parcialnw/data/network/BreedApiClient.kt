package com.kubernights.tp3.parcialnw.data.network

import com.kubernights.tp3.parcialnw.data.model.BreedsApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface BreedApiClient {
    @GET("/api/breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsApiResponse>
}