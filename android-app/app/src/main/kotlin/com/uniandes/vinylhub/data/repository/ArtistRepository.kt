package com.uniandes.vinylhub.data.repository

import com.uniandes.vinylhub.data.local.ArtistDao
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.data.remote.ArtistService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArtistRepository @Inject constructor(
    private val artistService: ArtistService,
    private val artistDao: ArtistDao
) {
    
    fun getAllArtists(): Flow<List<Artist>> = artistDao.getAllArtists()
    
    suspend fun getArtistById(id: Int): Artist? = artistDao.getArtistById(id)
    
    suspend fun refreshArtists() {
        try {
            val artists = artistService.getAllArtists()
            artistDao.insertArtists(artists)
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    suspend fun insertArtist(artist: Artist) {
        artistDao.insertArtist(artist)
    }
    
    suspend fun updateArtist(artist: Artist) {
        artistDao.updateArtist(artist)
    }
    
    suspend fun deleteArtist(artist: Artist) {
        artistDao.deleteArtist(artist)
    }
}

