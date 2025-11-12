package com.uniandes.vinylhub.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    
    private const val BASE_URL = "https://api.example.com/api/"
    
    // Fix: Usar lazy initialization para evitar crear múltiples instancias
    private val httpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    // Fix: Usar lazy initialization para evitar crear múltiples instancias
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    val albumService: AlbumService by lazy {
        retrofit.create(AlbumService::class.java)
    }
    
    val artistService: ArtistService by lazy {
        retrofit.create(ArtistService::class.java)
    }
    
    val collectorService: CollectorService by lazy {
        retrofit.create(CollectorService::class.java)
    }
}

