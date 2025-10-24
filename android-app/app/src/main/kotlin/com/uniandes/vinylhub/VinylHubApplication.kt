package com.uniandes.vinylhub

import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import okhttp3.OkHttpClient
import com.uniandes.vinylhub.di.DaggerAppComponent

class VinylHubApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        // Inicializar Coil con OkHttpClient simple
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient(okHttpClient)
            .build()

        Coil.setImageLoader(imageLoader)
        Log.d("VinylHubApplication", "Coil initialized successfully")
    }
}

