package com.uniandes.vinylhub

import android.app.Application
import com.uniandes.vinylhub.di.DaggerAppComponent

class VinylHubApplication : Application() {
    
    val appComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }
    
    override fun onCreate() {
        super.onCreate()
    }
}

