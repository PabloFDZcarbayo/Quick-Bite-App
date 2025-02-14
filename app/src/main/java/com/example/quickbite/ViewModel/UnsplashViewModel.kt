package com.example.quickbite.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.Data.Remote.Repositories.UnsplashRepository
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
    val categories = listOf(
        Category(1, "Main Course", ""),
        Category(2, "Side Dish", ""),
        Category(3, "Dessert", ""),
        Category(4, "Appetizer", ""),
        Category(5, "Salad", ""),
        Category(6, "Bread", ""),
        Category(7, "Breakfast", ""),
        Category(8, "Soup", ""),
        Category(9, "Beverage", ""),
        Category(10, "Sauce", ""),
        Category(11, "Marinade", ""),
        Category(12, "Finger Food", ""),
        Category(13, "Snack", ""),
        Category(14, "Drink", "")
    )


    /* Se le pasa una lista de categorias y hace las peticiones
    para conseguir las imagenes de manera asincrona.
     Luego las guarda las imagenes en la categoria*/
    fun getImages() {
        viewModelScope.launch {
            val response = categories.map { category ->
                async {
                    try {
                        val image = unsplashRepository.getRandomImage(
                            apiKey,
                            category.name
                        )
                        category.image = image ?: ""
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            response.awaitAll()
        }
    }
}