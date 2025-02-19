package com.example.quickbite.Data.Remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpoonacularService {

    @GET("recipes/complexSearch")
    suspend fun getRecipiesType(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Int = 50
    ): RecipesResponse


    data class RecipesResponse(
        val results: List<Recipe>
    )

    data class Recipe(
        val id: Int,
        val title: String,
        val image: String
    )

}