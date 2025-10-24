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
            android.util.Log.d("AlbumRepository", "Iniciando refreshAlbums...")
            val albumsFromApi = albumService.getAllAlbums()
            android.util.Log.d("AlbumRepository", "Álbumes obtenidos del API: ${albumsFromApi.size}")

            // Mapear los datos de la API al modelo de Room
            val albumsToSave = albumsFromApi.map { apiAlbum ->
                Album(
                    id = apiAlbum.id,
                    name = apiAlbum.name,
                    description = apiAlbum.description,
                    cover = apiAlbum.cover,
                    releaseDate = apiAlbum.releaseDate,
                    genre = apiAlbum.genre,
                    recordLabel = apiAlbum.recordLabel,
                    artists = apiAlbum.artists,
                    tracksCount = apiAlbum.tracks?.size ?: 0,
                    performersCount = apiAlbum.performers?.size ?: 0,
                    commentsCount = apiAlbum.comments?.size ?: 0
                )
            }
            // Borrar todos los álbumes antiguos antes de insertar los nuevos
            albumDao.deleteAllAlbums()
            albumDao.insertAlbums(albumsToSave)
            android.util.Log.d("AlbumRepository", "Álbumes guardados en BD: ${albumsToSave.size}")
        } catch (e: Exception) {
            // Handle error - log it
            android.util.Log.e("AlbumRepository", "Error refreshing albums: ${e.message}", e)
            e.printStackTrace()
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

