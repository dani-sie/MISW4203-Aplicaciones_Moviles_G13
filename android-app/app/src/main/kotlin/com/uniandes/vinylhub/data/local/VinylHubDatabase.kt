package com.uniandes.vinylhub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.data.model.Collector

@Database(
    entities = [Album::class, Artist::class, Collector::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VinylHubDatabase : RoomDatabase() {
    
    abstract fun albumDao(): AlbumDao
    abstract fun artistDao(): ArtistDao
    abstract fun collectorDao(): CollectorDao
    
    companion object {
        @Volatile
        private var INSTANCE: VinylHubDatabase? = null
        
        fun getDatabase(context: Context): VinylHubDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylHubDatabase::class.java,
                    "vinylhub_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

