package com.uniandes.vinylhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.model.Track
import com.uniandes.vinylhub.data.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    val albums: Flow<List<Album>> = albumRepository.getAllAlbums()

    init {
        // Cargar datos del backend al inicializar
        refreshAlbums()
    }

    fun refreshAlbums() {
        viewModelScope.launch {
            android.util.Log.d("AlbumViewModel", "refreshAlbums() called")
            albumRepository.refreshAlbums()
            android.util.Log.d("AlbumViewModel", "refreshAlbums() completed")
        }
    }

    suspend fun getAlbumById(id: Int): Album? {
        return albumRepository.getAlbumById(id)
    }

    suspend fun createAlbum(
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String
    ): Album? {
        return albumRepository.createAlbum(
            name = name,
            cover = cover,
            releaseDate = releaseDate,
            description = description,
            genre = genre,
            recordLabel = recordLabel
        )
    }

    suspend fun getAlbumTracks(albumId: Int): List<Track> {
        return albumRepository.getAlbumTracks(albumId)
    }

    suspend fun addTrackToAlbum(albumId: Int, name: String, duration: String): Track? {
        return albumRepository.addTrackToAlbum(albumId, name, duration)
    }
}

