package com.uniandes.vinylhub.data.repository

import com.uniandes.vinylhub.data.local.ArtistDao
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.data.remote.ArtistService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import android.util.Log

@OptIn(ExperimentalCoroutinesApi::class)
class ArtistRepository @Inject constructor(
    private val artistService: ArtistService,
    private val artistDao: ArtistDao
) {
    private var artistsInMemory: Map<Int, Artist> = emptyMap()
    private var isInitialized = false

    fun getAllArtists(): Flow<List<Artist>> = artistDao.getAllArtists()
        .transformLatest { dbArtists ->
            // Si no está inicializado, cargar del API primero
            if (!isInitialized) {
                Log.d("ArtistRepository", "getAllArtists: No inicializado, cargando del API...")
                try {
                    refreshArtists()
                } catch (e: Exception) {
                    Log.e("ArtistRepository", "Error loading artists: ${e.message}")
                }
            }

            // Enriquecer los datos de la BD con los datos en memoria
            val enrichedArtists = dbArtists.map { dbArtist ->
                val artistInMemory = artistsInMemory[dbArtist.id]
                if (artistInMemory != null) {
                    artistInMemory
                } else {
                    dbArtist
                }
            }

            emit(enrichedArtists)
        }

    suspend fun getArtistById(id: Int): Artist? = artistDao.getArtistById(id)

    suspend fun refreshArtists() {
        try {
            val apiArtists = artistService.getAllArtists()
            val artists = apiArtists.map { apiArtist ->
                Artist(
                    id = apiArtist.id,
                    name = apiArtist.name,
                    birthDate = apiArtist.birthDate,
                    image = apiArtist.image,
                    description = apiArtist.description,
                    albumsCount = apiArtist.albums?.size ?: 0,
                    prizesCount = apiArtist.performerPrizes?.size ?: 0
                )
            }

            // Guardar en memoria
            artistsInMemory = artists.associateBy { it.id }
            isInitialized = true
            Log.d("ArtistRepository", "Datos en memoria: ${artistsInMemory.size} artistas")

            // Guardar en BD
            artistDao.insertArtists(artists)
            Log.d("ArtistRepository", "Artistas guardados en BD: ${artists.size}")
        } catch (e: Exception) {
            Log.e("ArtistRepository", "Error refreshing artists: ${e.message}", e)
            e.printStackTrace()
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

