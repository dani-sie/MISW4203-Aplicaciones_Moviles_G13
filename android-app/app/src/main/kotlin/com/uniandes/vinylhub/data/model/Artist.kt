package com.uniandes.vinylhub.data.model

import androidx.room.Entity
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
)

