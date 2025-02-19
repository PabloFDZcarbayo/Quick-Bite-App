package com.example.quickbite.Data.Remote.Repositories

import com.example.quickbite.Data.Remote.UnsplashService
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val unsplashService: UnsplashService) {

    suspend fun getRandomImage(apikey: String, query: String): UnsplashService.ImageResponse? {
        return try {
            unsplashService.getRandomImage(apikey, query).firstOrNull()
        } catch (Exception: Exception) {
            return null
        }
    }

}