package com.example.quickbite.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.Data.Remote.Repositories.UnsplashRepository
import com.example.quickbite.Data.Remote.UnsplashService
import com.example.quickbite.Model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UnsplashViewModel @Inject constructor(private val unsplashRepository: UnsplashRepository) :
    ViewModel() {
    private val apiKey = "2JduZMCeKvOAys0X7xMmBsGwe71h0a2xY5WTWPguy8k"

    private val _categories = mutableStateListOf<Category>()
    val categories: List<Category> get() = _categories

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    init {
        _categories.addAll(
            listOf(
                Category(1, "Main Course", "","aliment"),
                Category(2, "Side Dish", "","side dish"),
                Category(3, "Dessert", "","dessert"),
                Category(4, "Appetizer", "", "appetizer"),
                Category(5, "Salad", "", "salad"),
                Category(6, "Bread", "", "bread"),
                Category(7, "Breakfast", "", "breakfast"),
                Category(8, "Soup", "", "soup"),
                Category(9, "Beverage", "","beverage"),
                Category(10, "Sauce", "","sauce"),
                Category(11, "Marinade", "", "marinade"),
                Category(12, "Finger Food", "","finger food"),
                Category(13, "Snack", "","snack"),
                Category(14, "Drink", "", "drink")
            )
        )
        getImages()

    }


    /* Se le pasa una lista de categorias y hace las peticiones
    para conseguir las imagenes de manera asincrona.
     Luego las guarda las imagenes en la categoria*/
    fun getImages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _categories.forEachIndexed() { index, category ->
                    val imageResponse = unsplashRepository.getRandomImage(apiKey, category.filter)
                    val imageUrl = imageResponse?.urls?.small
                    if (imageUrl != null) {
                        _categories[index] = category.copy(image = imageUrl)
                    }
                }
            } catch (Exception: Exception) {
                Log.d("Unsplah", "API Exception: $Exception")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
