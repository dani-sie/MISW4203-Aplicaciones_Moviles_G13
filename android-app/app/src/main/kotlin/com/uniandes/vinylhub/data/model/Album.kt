package com.uniandes.vinylhub.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// Modelo para la base de datos Room
@Entity(tableName = "albums")
data class Album(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("cover")
    val cover: String = "",

    @SerializedName("releaseDate")
    val releaseDate: String = "",

    @SerializedName("genre")
    val genre: String = "",

    @SerializedName("recordLabel")
    val recordLabel: String = "",

    @SerializedName("artists")
    val artists: List<Int> = emptyList(),

    val tracksCount: Int = 0,
    val performersCount: Int = 0,
    val commentsCount: Int = 0
) {
    // Estos campos se deserializan desde JSON pero no se persisten en Room
    @Ignore
    @SerializedName("tracks")
    var tracks: List<Track> = emptyList()

    @Ignore
    @SerializedName("performers")
    var performers: List<Performer> = emptyList()

    @Ignore
    @SerializedName("comments")
    var comments: List<Comment> = emptyList()
}

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

