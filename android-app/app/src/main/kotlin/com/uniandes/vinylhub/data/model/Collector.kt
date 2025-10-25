package com.uniandes.vinylhub.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "collectors")
data class Collector(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("telephone")
    val telephone: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("image")
    val image: String? = null,

    val commentsCount: Int = 0,
    val favoritesCount: Int = 0,
    val albumsCount: Int = 0
) {
    @Ignore
    @SerializedName("comments")
    var comments: List<CollectorComment> = emptyList()

    @Ignore
    @SerializedName("favoritePerformers")
    var favoritePerformers: List<FavoritePerformer> = emptyList()

    @Ignore
    @SerializedName("collectorAlbums")
    var collectorAlbums: List<CollectorAlbum> = emptyList()
}

data class CollectorComment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("rating")
    val rating: Int
)

data class FavoritePerformer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String = ""
)

data class CollectorAlbum(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("status")
    val status: String
)

