package com.example.quickbite.Data.Remote

import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashService {

    /* Nos permite obtener imagenes aleatorias de la API de Unsplash,
    con query elegiremos la categoria de imagenes que obtenemos, la apiKey necesaria para
    acceder a la API y count indica que solo recibiremos una imagen
     */

    @GET("photos/random")
    suspend fun getRandomImage(
        @Query("client_id") apiKey: String,
        @Query("query") query: String,
        @Query("count") count: Int = 1
    ): ImageResponse


    data class ImageResponse(
        val url: Urls
    )

    data class Urls(
        val regular: String
    )


}