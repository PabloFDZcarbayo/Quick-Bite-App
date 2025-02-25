package com.example.quickbite.Core.DI

import com.example.quickbite.Data.Remote.SpoonacularService
import com.example.quickbite.Data.Remote.UnsplashService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //API de spooncacular, la usaremos para conseguir recetas
    val spoonacular_url = "https://api.spoonacular.com/"

    //API de unsplash, la usaremos para conseguir imagenes
    val unsplash_url = "https://api.unsplash.com/"


    @Provides
    @Singleton
    @Named("spoonacular")
    fun provideSpoonacular(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(spoonacular_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    @Named("unsplash")
    fun provideUnsplash(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(unsplash_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    @Provides
    @Singleton
    fun provideUnsplashService(@Named("unsplash") retrofit: Retrofit): UnsplashService =
        retrofit.create(UnsplashService::class.java)


    @Provides
    @Singleton
    fun provideSpoonacularService(@Named("spoonacular") retrofit: Retrofit): SpoonacularService =
        retrofit.create(SpoonacularService::class.java)


}
