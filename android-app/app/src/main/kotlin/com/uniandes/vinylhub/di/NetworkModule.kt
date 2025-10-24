package com.uniandes.vinylhub.di

import com.uniandes.vinylhub.data.remote.AlbumService
import com.uniandes.vinylhub.data.remote.ArtistService
import com.uniandes.vinylhub.data.remote.CollectorService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    
    private companion object {
        const val BASE_URL = "http://10.0.2.2:3000/"
    }
    
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }
    
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Singleton
    @Provides
    fun provideAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }
    
    @Singleton
    @Provides
    fun provideArtistService(retrofit: Retrofit): ArtistService {
        return retrofit.create(ArtistService::class.java)
    }
    
    @Singleton
    @Provides
    fun provideCollectorService(retrofit: Retrofit): CollectorService {
        return retrofit.create(CollectorService::class.java)
    }
}

