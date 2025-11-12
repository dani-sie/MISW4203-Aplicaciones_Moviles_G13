package com.uniandes.vinylhub

import android.app.Application
import android.util.Log
import coil.Coil
import com.uniandes.vinylhub.di.DaggerAppComponent

class VinylHubApplication : Application() {

    val appComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        // Fix: Usar ImageLoader de Dagger en lugar de crear uno nuevo
        // Esto evita crear múltiples instancias de OkHttpClient y ImageLoader
        val imageLoader = appComponent.imageLoader()
        Coil.setImageLoader(imageLoader)
        Log.d("VinylHubApplication", "Coil initialized successfully with Dagger ImageLoader")
    }
}

