package com.uniandes.vinylhub.di

import android.content.Context
import android.util.Log
import coil.ImageLoader
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class CoilModule {

    @Singleton
    @Provides
    @CoilOkHttp
    fun provideCoilOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d("CoilOkHttp", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }

        val userAgentInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Sec-Fetch-Dest", "image")
                .header("Sec-Fetch-Mode", "no-cors")
                .header("Sec-Fetch-Site", "cross-site")
                .build()
            chain.proceed(requestWithHeaders)
        }

        val debugInterceptor = Interceptor { chain ->
            val request = chain.request()
            Log.d("CoilInterceptor", "Loading image: ${request.url}")
            try {
                val response = chain.proceed(request)
                Log.d("CoilInterceptor", "Response code: ${response.code} for ${request.url}")
                response
            } catch (e: Exception) {
                Log.e("CoilInterceptor", "Error loading ${request.url}: ${e.message}", e)
                throw e
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(userAgentInterceptor)
            .addInterceptor(debugInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideImageLoader(context: Context, @CoilOkHttp okHttpClient: OkHttpClient): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .logger(DebugLogger())
            .build()
    }
}

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoilOkHttp

