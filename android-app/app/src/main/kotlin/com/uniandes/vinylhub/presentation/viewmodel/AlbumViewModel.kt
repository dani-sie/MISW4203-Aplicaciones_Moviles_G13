package com.uniandes.vinylhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinylhub.data.model.Album
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
}

