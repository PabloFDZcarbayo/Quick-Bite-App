package com.example.quickbite.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.Data.Remote.Repositories.SpoonacularRepository
import com.example.quickbite.Data.Remote.SpoonacularService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpoonacularViewModel @Inject constructor(private val spoonacularRepository: SpoonacularRepository,
) : ViewModel() {

    private val apiKey = "aef4d533aaa54d0ea4ce0e58a213781e"

    private val _recipesList = mutableStateListOf<SpoonacularService.Recipe>()
    val recipesList: SnapshotStateList<SpoonacularService.Recipe> get() = _recipesList

    private val _instruction = mutableStateListOf<SpoonacularService.Step>()
    val instruction: SnapshotStateList<SpoonacularService.Step> get() = _instruction


    fun getRecipesType(categoryName: String) {
        viewModelScope.launch {
            try {
                val recipesResponse = spoonacularRepository.getRecipesType(apiKey, categoryName)
                Log.d ("SPOONACULAR", "PETICION A API: $recipesResponse")
                _recipesList.clear()
                _recipesList.addAll(recipesResponse)
            } catch (Exception: Exception) {
                Log.d("Spoonacular", "API Exception: $Exception")
            }
        }
    }

    fun getInstructions(id: Int) {
        viewModelScope.launch {
            try {
                val instructionsResponse = spoonacularRepository.getInstructions(apiKey, id)
                Log.d ("SPOONACULAR", "PETICION A API: $instructionsResponse")
                _instruction.clear()
                _instruction.addAll(instructionsResponse.first().steps)
            } catch (Exception: Exception) {
                Log.d("Spoonacular", "API Exception: $Exception")
            }
        }
    }
}


