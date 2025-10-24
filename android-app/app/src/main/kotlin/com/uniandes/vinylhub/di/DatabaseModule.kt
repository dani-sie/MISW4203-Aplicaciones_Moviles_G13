package com.uniandes.vinylhub.di

import android.content.Context
import androidx.room.Room
import com.uniandes.vinylhub.data.local.AlbumDao
import com.uniandes.vinylhub.data.local.ArtistDao
import com.uniandes.vinylhub.data.local.CollectorDao
import com.uniandes.vinylhub.data.local.VinylHubDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    
    @Singleton
    @Provides
    fun provideDatabase(context: Context): VinylHubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            VinylHubDatabase::class.java,
            "vinylhub_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }
    
    @Singleton
    @Provides
    fun provideAlbumDao(database: VinylHubDatabase): AlbumDao {
        return database.albumDao()
    }
    
    @Singleton
    @Provides
    fun provideArtistDao(database: VinylHubDatabase): ArtistDao {
        return database.artistDao()
    }
    
    @Singleton
    @Provides
    fun provideCollectorDao(database: VinylHubDatabase): CollectorDao {
        return database.collectorDao()
    }
}

