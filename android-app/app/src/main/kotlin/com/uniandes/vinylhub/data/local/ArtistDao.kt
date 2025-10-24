package com.uniandes.vinylhub.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uniandes.vinylhub.data.model.Artist
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtistDao {
    
    @Query("SELECT * FROM artists")
    fun getAllArtists(): Flow<List<Artist>>
    
    @Query("SELECT * FROM artists WHERE id = :id")
    suspend fun getArtistById(id: Int): Artist?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<Artist>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtist(artist: Artist)
    
    @Update
    suspend fun updateArtist(artist: Artist)
    
    @Delete
    suspend fun deleteArtist(artist: Artist)
    
    @Query("DELETE FROM artists")
    suspend fun deleteAllArtists()
}

