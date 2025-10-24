package com.uniandes.vinylhub.data.remote

import com.uniandes.vinylhub.data.model.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    
    @GET("albums")
    suspend fun getAllAlbums(): List<Album>
    
    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Album
}

