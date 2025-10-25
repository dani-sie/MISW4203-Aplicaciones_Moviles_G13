package com.uniandes.vinylhub.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistService {

    @GET("musicians")
    suspend fun getAllArtists(): List<ArtistApiResponse>

    @GET("musicians/{id}")
    suspend fun getArtistById(@Path("id") id: Int): ArtistApiResponse
}

data class ArtistApiResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("birthDate")
    val birthDate: String,

    @SerializedName("image")
    val image: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("albums")
    val albums: List<ArtistAlbum>? = null,

    @SerializedName("performerPrizes")
    val performerPrizes: List<ArtistPrize>? = null
)

data class ArtistAlbum(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("cover")
    val cover: String? = "",

    @SerializedName("releaseDate")
    val releaseDate: String? = "",

    @SerializedName("description")
    val description: String? = "",

    @SerializedName("genre")
    val genre: String? = "",

    @SerializedName("recordLabel")
    val recordLabel: String? = ""
)

data class ArtistPrize(
    @SerializedName("id")
    val id: Int,

    @SerializedName("premiationDate")
    val premiationDate: String
)

