package com.uniandes.vinylhub.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey
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
    val artists: List<Int> = emptyList()
)

