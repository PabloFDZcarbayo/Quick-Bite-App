package com.example.quickbite.Data.Remote.Repositories

import com.example.quickbite.Data.Remote.UnsplashService
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val unsplashService: UnsplashService) {

    suspend fun getRandomImage(apikey: String, query: String): String? {
        try {
            return unsplashService.getRandomImage(apikey, query).url.regular
        } catch (Exception: Exception) {
            return null
        }
    }

}