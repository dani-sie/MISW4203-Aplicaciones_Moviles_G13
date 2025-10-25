package com.uniandes.vinylhub.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.data.repository.ArtistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistViewModel @Inject constructor(
    private val artistRepository: ArtistRepository
) : ViewModel() {

    val artists: Flow<List<Artist>> = artistRepository.getAllArtists()

    init {
        // Cargar datos del backend al inicializar
        refreshArtists()
    }

    fun refreshArtists() {
        viewModelScope.launch {
            android.util.Log.d("ArtistViewModel", "refreshArtists() called")
            artistRepository.refreshArtists()
            android.util.Log.d("ArtistViewModel", "refreshArtists() completed")
        }
    }

    suspend fun getArtistById(id: Int): Artist? {
        return artistRepository.getArtistById(id)
    }
}

