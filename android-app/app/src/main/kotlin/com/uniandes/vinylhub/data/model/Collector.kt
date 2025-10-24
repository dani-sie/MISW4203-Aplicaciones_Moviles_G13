package com.uniandes.vinylhub.data.model

import androidx.room.Entity
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
    val email: String
)

