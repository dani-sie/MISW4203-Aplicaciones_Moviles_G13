package com.uniandes.vinylhub.data.remote

import com.uniandes.vinylhub.data.model.Artist
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistService {
    
    @GET("artists")
    suspend fun getAllArtists(): List<Artist>
    
    @GET("artists/{id}")
    suspend fun getArtistById(@Path("id") id: Int): Artist
}

