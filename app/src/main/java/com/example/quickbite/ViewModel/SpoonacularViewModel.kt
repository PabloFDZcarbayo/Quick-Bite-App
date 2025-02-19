package com.example.quickbite.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.Data.Remote.Repositories.SpoonacularRepository
import com.example.quickbite.Data.Remote.SpoonacularService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpoonacularViewModel @Inject constructor(private val spoonacularRepository: SpoonacularRepository,
) : ViewModel() {

    private val apiKey = "aef4d533aaa54d0ea4ce0e58a213781e"

    private val _recipiesList = mutableStateListOf<SpoonacularService.Recipe>()
    val recipesList: SnapshotStateList<SpoonacularService.Recipe> get() = _recipiesList


    fun getRecipesType(categoryName: String) {
        viewModelScope.launch {
            try {
                val recipesResponse = spoonacularRepository.getRecipesType(apiKey, categoryName)
                Log.d ("SPOONACULAR", "PETICION A API: $recipesResponse")
                _recipiesList.clear()
                _recipiesList.addAll(recipesResponse)
            } catch (Exception: Exception) {
                Log.d("Spoonacular", "API Exception: $Exception")
            }
        }
    }
}


