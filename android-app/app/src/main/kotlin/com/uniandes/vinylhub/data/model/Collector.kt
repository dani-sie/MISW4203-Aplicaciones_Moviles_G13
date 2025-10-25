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
    val name: String = "",

    @SerializedName("telephone")
    val telephone: String = "",

    @SerializedName("email")
    val email: String = "",

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
    val description: String = "",
    @SerializedName("rating")
    val rating: Int = 0
)

data class FavoritePerformer(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("birthDate")
    val birthDate: String? = null,
    @SerializedName("creationDate")
    val creationDate: String? = null
) {
    // Helper para obtener la fecha (birthDate para artistas, creationDate para bandas)
    fun getDate(): String {
        return when {
            !birthDate.isNullOrEmpty() -> birthDate
            !creationDate.isNullOrEmpty() -> creationDate
            else -> ""
        }
    }
}

data class CollectorAlbum(
    @SerializedName("id")
    val id: Int,
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("status")
    val status: String = ""
)

