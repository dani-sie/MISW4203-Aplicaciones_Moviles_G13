package com.uniandes.vinylhub.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uniandes.vinylhub.data.model.Album
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    
    @Query("SELECT * FROM albums")
    fun getAllAlbums(): Flow<List<Album>>
    
    @Query("SELECT * FROM albums WHERE id = :id")
    suspend fun getAlbumById(id: Int): Album?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbums(albums: List<Album>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: Album)
    
    @Update
    suspend fun updateAlbum(album: Album)
    
    @Delete
    suspend fun deleteAlbum(album: Album)
    
    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()
}

