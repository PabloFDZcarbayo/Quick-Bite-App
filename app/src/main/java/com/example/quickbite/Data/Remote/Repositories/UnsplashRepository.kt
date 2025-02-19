package com.example.quickbite.Data.Remote.Repositories

import com.example.quickbite.Data.Remote.UnsplashService
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val unsplashService: UnsplashService) {

    suspend fun getRandomImage(apikey: String, query: String): UnsplashService.ImageResponse?{
        try {
            val images = unsplashService.getRandomImage(apikey, query)
            return images.firstOrNull()
        } catch (Exception: Exception) {
            return null
        }
    }

}