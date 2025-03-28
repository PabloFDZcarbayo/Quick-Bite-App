package com.example.quickbite.Data.Remote


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularService {

    @GET("recipes/complexSearch")
    suspend fun getRecipiesType(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("number") number: Int = 5
    ): RecipesResponse

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getInstructions(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,

    ):List<InstructionsResponse>




    data class RecipesResponse(
        val results: List<Recipe>
    )

    @Parcelize
    @Serializable
    data class Recipe(
        val id: Int,
        val title: String,
        val image: String
    ) : Parcelable



    data class InstructionsResponse(
        val name: String,
        val steps: List<Step>
    )

    @Serializable
    data class Step(
        val number: Long,
        val step: String,
        val ingredients: List<Ent>,

        )

    @Serializable
    data class Ent(
        val id: Long,
        val name: String,
        val localizedName: String,
        val image: String
    )
}