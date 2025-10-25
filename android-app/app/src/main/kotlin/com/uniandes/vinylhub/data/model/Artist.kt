package com.uniandes.vinylhub.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey
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

    val albumsCount: Int = 0,
    val prizesCount: Int = 0
) {
    // Estos campos se deserializan desde JSON pero no se persisten en Room
    @Ignore
    @SerializedName("albums")
    var albums: List<ArtistAlbum> = emptyList()

    @Ignore
    @SerializedName("performerPrizes")
    var performerPrizes: List<ArtistPrize> = emptyList()
}

data class ArtistAlbum(
    @SerializedName("id")
    val id: Int,

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

data class ArtistPrize(
    @SerializedName("id")
    val id: Int,

    @SerializedName("premiationDate")
    val premiationDate: String
)

