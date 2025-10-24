package com.uniandes.vinylhub.di

import android.content.Context
import com.uniandes.vinylhub.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, NetworkModule::class])
interface AppComponent {
    
    fun inject(activity: MainActivity)
    
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        
        fun build(): AppComponent
    }
}

