package com.uniandes.vinylhub.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlbumService {

    @GET("albums")
    suspend fun getAllAlbums(): List<AlbumApiResponse>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): AlbumApiResponse

    @POST("albums")
    suspend fun createAlbum(@Body album: CreateAlbumRequest): AlbumApiResponse

    @GET("albums/{albumId}/tracks")
    suspend fun getAlbumTracks(@Path("albumId") albumId: Int): List<Track>

    @POST("albums/{albumId}/tracks")
    suspend fun addTrackToAlbum(
        @Path("albumId") albumId: Int,
        @Body track: CreateTrackRequest
    ): TrackApiResponse
}

data class AlbumApiResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("cover")
    val cover: String,

    @SerializedName("releaseDate")
    val releaseDate: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("recordLabel")
    val recordLabel: String,

    @SerializedName("artists")
    val artists: List<Int>? = null,

    @SerializedName("tracks")
    val tracks: List<Track>? = null,

    @SerializedName("performers")
    val performers: List<Performer>? = null,

    @SerializedName("comments")
    val comments: List<Comment>? = null
)

data class Track(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("duration")
    val duration: String
)

data class Performer(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("birthDate")
    val birthDate: String? = null,

    @SerializedName("creationDate")
    val creationDate: String? = null
)

data class Comment(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("rating")
    val rating: Int
)

data class CreateAlbumRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("cover")
    val cover: String,

    @SerializedName("releaseDate")
    val releaseDate: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("genre")
    val genre: String,

    @SerializedName("recordLabel")
    val recordLabel: String
)

data class CreateTrackRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("duration")
    val duration: String
)

data class TrackApiResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("duration")
    val duration: String,

    @SerializedName("album")
    val album: AlbumApiResponse? = null
)

