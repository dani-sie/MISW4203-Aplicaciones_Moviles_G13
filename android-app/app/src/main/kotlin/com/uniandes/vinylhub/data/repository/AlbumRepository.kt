package com.uniandes.vinylhub.data.repository

import com.uniandes.vinylhub.data.local.AlbumDao
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.model.Track
import com.uniandes.vinylhub.data.model.Performer
import com.uniandes.vinylhub.data.model.Comment
import com.uniandes.vinylhub.data.remote.AlbumService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumRepository @Inject constructor(
    private val albumService: AlbumService,
    private val albumDao: AlbumDao
) {
    // Mantener una copia en memoria de los datos completos con tracks, performers y comments
    private var albumsInMemory: Map<Int, Album> = emptyMap()
    private var isInitialized = false

    fun getAllAlbums(): Flow<List<Album>> = albumDao.getAllAlbums()
        .transformLatest { dbAlbums ->
            // Si no está inicializado, cargar del API primero
            if (!isInitialized) {
                android.util.Log.d("AlbumRepository", "getAllAlbums: No inicializado, cargando del API...")
                try {
                    refreshAlbums()
                } catch (e: Exception) {
                    android.util.Log.e("AlbumRepository", "Error loading albums: ${e.message}")
                }
            }

            // Enriquecer los datos de la BD con los datos en memoria
            val enrichedAlbums = dbAlbums.map { dbAlbum ->
                val albumInMemory = albumsInMemory[dbAlbum.id]
                if (albumInMemory != null) {
                    // Si existe en memoria, usar los datos completos
                    albumInMemory
                } else {
                    // Si no existe en memoria, usar los datos de la BD
                    dbAlbum
                }
            }

            android.util.Log.d("AlbumRepository", "getAllAlbums: Emitiendo ${enrichedAlbums.size} álbumes")
            enrichedAlbums.forEach { album ->
                android.util.Log.d("AlbumRepository", "Album ${album.id}: tracks=${album.tracks.size}, performers=${album.performers.size}, comments=${album.comments.size}")
            }

            this@transformLatest.emit(enrichedAlbums)
        }
    
    suspend fun getAlbumById(id: Int): Album? {
        try {
            android.util.Log.d("AlbumRepository", "Obteniendo álbum $id del API...")
            val apiAlbum = albumService.getAlbumById(id)

            // Convertir a Album con datos completos
            val album = Album(
                id = apiAlbum.id,
                name = apiAlbum.name,
                description = apiAlbum.description,
                cover = apiAlbum.cover,
                releaseDate = apiAlbum.releaseDate,
                genre = apiAlbum.genre,
                recordLabel = apiAlbum.recordLabel,
                artists = apiAlbum.artists ?: emptyList(),
                tracksCount = apiAlbum.tracks?.size ?: 0,
                performersCount = apiAlbum.performers?.size ?: 0,
                commentsCount = apiAlbum.comments?.size ?: 0
            ).apply {
                tracks = apiAlbum.tracks?.map { Track(it.id, it.name, it.duration) } ?: emptyList()
                performers = apiAlbum.performers?.map { Performer(it.id, it.name, it.image, it.description, it.birthDate, it.creationDate) } ?: emptyList()
                comments = apiAlbum.comments?.map { Comment(it.id, it.description, it.rating) } ?: emptyList()
            }

            android.util.Log.d("AlbumRepository", "Álbum $id obtenido: tracks=${album.tracks.size}, performers=${album.performers.size}, comments=${album.comments.size}")
            return album
        } catch (e: Exception) {
            android.util.Log.e("AlbumRepository", "Error obteniendo álbum $id: ${e.message}")
            return null
        }
    }
    
    suspend fun refreshAlbums() {
        try {
            android.util.Log.d("AlbumRepository", "Iniciando refreshAlbums...")
            val albumsFromApi = albumService.getAllAlbums()
            android.util.Log.d("AlbumRepository", "Álbumes obtenidos del API: ${albumsFromApi.size}")

            // Convertir a Album y guardar en memoria
            val albumsWithData = albumsFromApi.map { apiAlbum ->
                Album(
                    id = apiAlbum.id,
                    name = apiAlbum.name,
                    description = apiAlbum.description,
                    cover = apiAlbum.cover,
                    releaseDate = apiAlbum.releaseDate,
                    genre = apiAlbum.genre,
                    recordLabel = apiAlbum.recordLabel,
                    artists = apiAlbum.artists ?: emptyList(),
                    tracksCount = apiAlbum.tracks?.size ?: 0,
                    performersCount = apiAlbum.performers?.size ?: 0,
                    commentsCount = apiAlbum.comments?.size ?: 0
                ).apply {
                    tracks = apiAlbum.tracks?.map { Track(it.id, it.name, it.duration) } ?: emptyList()
                    performers = apiAlbum.performers?.map { Performer(it.id, it.name, it.image, it.description, it.birthDate, it.creationDate) } ?: emptyList()
                    comments = apiAlbum.comments?.map { Comment(it.id, it.description, it.rating) } ?: emptyList()
                }
            }

            // Guardar los datos completos en memoria
            albumsInMemory = albumsWithData.associateBy { it.id }
            isInitialized = true
            android.util.Log.d("AlbumRepository", "Datos en memoria: ${albumsInMemory.size} álbumes")
            albumsInMemory.forEach { (id, album) ->
                android.util.Log.d("AlbumRepository", "Album $id: tracks=${album.tracks.size}, performers=${album.performers.size}, comments=${album.comments.size}")
            }

            // Mapear los datos de la API al modelo de Room (sin los datos complejos)
            val albumsToSave = albumsWithData.map { album ->
                Album(
                    id = album.id,
                    name = album.name,
                    description = album.description,
                    cover = album.cover,
                    releaseDate = album.releaseDate,
                    genre = album.genre,
                    recordLabel = album.recordLabel,
                    artists = album.artists,
                    tracksCount = album.tracksCount,
                    performersCount = album.performersCount,
                    commentsCount = album.commentsCount
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

    suspend fun createAlbum(
        name: String,
        cover: String,
        releaseDate: String,
        description: String,
        genre: String,
        recordLabel: String
    ): Album? {
        return try {
            android.util.Log.d("AlbumRepository", "Creando álbum: $name")

            val request = com.uniandes.vinylhub.data.remote.CreateAlbumRequest(
                name = name,
                cover = cover,
                releaseDate = releaseDate,
                description = description,
                genre = genre,
                recordLabel = recordLabel
            )

            val apiResponse = albumService.createAlbum(request)
            android.util.Log.d("AlbumRepository", "Álbum creado con ID: ${apiResponse.id}")

            // Convertir la respuesta a Album
            val album = Album(
                id = apiResponse.id,
                name = apiResponse.name,
                description = apiResponse.description,
                cover = apiResponse.cover,
                releaseDate = apiResponse.releaseDate,
                genre = apiResponse.genre,
                recordLabel = apiResponse.recordLabel,
                artists = apiResponse.artists ?: emptyList(),
                tracksCount = apiResponse.tracks?.size ?: 0,
                performersCount = apiResponse.performers?.size ?: 0,
                commentsCount = apiResponse.comments?.size ?: 0
            ).apply {
                tracks = apiResponse.tracks?.map { Track(it.id, it.name, it.duration) } ?: emptyList()
                performers = apiResponse.performers?.map { Performer(it.id, it.name, it.image, it.description, it.birthDate, it.creationDate) } ?: emptyList()
                comments = apiResponse.comments?.map { Comment(it.id, it.description, it.rating) } ?: emptyList()
            }

            // Guardar en la base de datos local
            albumDao.insertAlbum(album)

            // Actualizar la memoria
            albumsInMemory = albumsInMemory + (album.id to album)

            android.util.Log.d("AlbumRepository", "Álbum guardado localmente")
            album
        } catch (e: Exception) {
            android.util.Log.e("AlbumRepository", "Error creando álbum: ${e.message}", e)
            null
        }
    }

    suspend fun getAlbumTracks(albumId: Int): List<Track> {
        return try {
            android.util.Log.d("AlbumRepository", "Obteniendo tracks del álbum $albumId")
            val tracks = albumService.getAlbumTracks(albumId)
            android.util.Log.d("AlbumRepository", "Tracks obtenidos: ${tracks.size}")
            tracks.map { Track(it.id, it.name, it.duration) }
        } catch (e: Exception) {
            android.util.Log.e("AlbumRepository", "Error obteniendo tracks: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun addTrackToAlbum(albumId: Int, name: String, duration: String): Track? {
        return try {
            android.util.Log.d("AlbumRepository", "Agregando track '$name' al álbum $albumId")

            val request = com.uniandes.vinylhub.data.remote.CreateTrackRequest(
                name = name,
                duration = duration
            )

            val apiResponse = albumService.addTrackToAlbum(albumId, request)
            android.util.Log.d("AlbumRepository", "Track creado con ID: ${apiResponse.id}")

            val track = Track(
                id = apiResponse.id,
                name = apiResponse.name,
                duration = apiResponse.duration
            )

            // Actualizar el álbum en memoria con el nuevo track
            val albumInMemory = albumsInMemory[albumId]
            if (albumInMemory != null) {
                val updatedTracks = albumInMemory.tracks + track
                val updatedAlbum = albumInMemory.copy(
                    tracksCount = updatedTracks.size
                ).apply {
                    tracks = updatedTracks
                }
                albumsInMemory = albumsInMemory + (albumId to updatedAlbum)
                android.util.Log.d("AlbumRepository", "Álbum actualizado en memoria con nuevo track")
            }

            track
        } catch (e: Exception) {
            android.util.Log.e("AlbumRepository", "Error agregando track: ${e.message}", e)
            null
        }
    }
}

