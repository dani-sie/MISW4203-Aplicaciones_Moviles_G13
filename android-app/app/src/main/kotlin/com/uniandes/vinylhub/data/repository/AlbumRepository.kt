package com.uniandes.vinylhub.data.repository

import com.uniandes.vinylhub.data.local.AlbumDao
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.remote.AlbumService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao
) {
    
    fun getAllAlbums(): Flow<List<Album>> = albumDao.getAllAlbums()
    
    suspend fun getAlbumById(id: Int): Album? = albumDao.getAlbumById(id)
    
    suspend fun refreshAlbums() {
        try {
            val albums = albumService.getAllAlbums()
            albumDao.insertAlbums(albums)
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    suspend fun insertAlbum(album: Album) {
        albumDao.insertAlbum(album)
    }
    
    suspend fun updateAlbum(album: Album) {
        albumDao.updateAlbum(album)
    }
    
    suspend fun deleteAlbum(album: Album) {
        albumDao.deleteAlbum(album)
    }
}

