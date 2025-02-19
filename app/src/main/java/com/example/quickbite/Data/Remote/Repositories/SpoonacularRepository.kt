package com.example.quickbite.Data.Remote.Repositories

import android.util.Log
import com.example.quickbite.Data.Remote.SpoonacularService
import javax.inject.Inject

class SpoonacularRepository @Inject constructor(private val spoonacularService: SpoonacularService) {

    suspend fun getRecipesType(apiKey: String, type: String): List<SpoonacularService.Recipe> {
        return try {
            spoonacularService.getRecipiesType(apiKey, type).results
        } catch (Exception: Exception) {
            Log.d("SPOONACULAR", "API EXCEPTION: $Exception")
            emptyList()
        }
    }
}


