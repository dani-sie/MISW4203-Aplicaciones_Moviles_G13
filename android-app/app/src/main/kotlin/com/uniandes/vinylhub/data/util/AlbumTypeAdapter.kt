package com.uniandes.vinylhub.data.util

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.model.Comment
import com.uniandes.vinylhub.data.model.Performer
import com.uniandes.vinylhub.data.model.Track
import java.lang.reflect.Type

class AlbumTypeAdapter : JsonDeserializer<Album> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Album {
        val jsonObject = json.asJsonObject

        Log.d("AlbumTypeAdapter", "Deserializing album: ${jsonObject.get("name")?.asString}")
        Log.d("AlbumTypeAdapter", "Has tracks: ${jsonObject.has("tracks")}")
        Log.d("AlbumTypeAdapter", "Has performers: ${jsonObject.has("performers")}")
        Log.d("AlbumTypeAdapter", "Has comments: ${jsonObject.has("comments")}")

        // Deserializar los campos principales
        val id = jsonObject.get("id").asInt
        val name = jsonObject.get("name")?.asString ?: ""
        val description = jsonObject.get("description")?.asString ?: ""
        val cover = jsonObject.get("cover")?.asString ?: ""
        val releaseDate = jsonObject.get("releaseDate")?.asString ?: ""
        val genre = jsonObject.get("genre")?.asString ?: ""
        val recordLabel = jsonObject.get("recordLabel")?.asString ?: ""

        val artistsArray = jsonObject.getAsJsonArray("artists")
        val artists = artistsArray?.map { it.asInt } ?: emptyList()

        // Deserializar tracks
        var tracks: List<Track> = emptyList()
        if (jsonObject.has("tracks") && !jsonObject.get("tracks").isJsonNull) {
            val tracksArray = jsonObject.getAsJsonArray("tracks")
            Log.d("AlbumTypeAdapter", "Tracks count: ${tracksArray.size()}")
            val trackListType = object : TypeToken<List<Track>>() {}.type
            tracks = context.deserialize(tracksArray, trackListType) ?: emptyList()
        }

        // Deserializar performers
        var performers: List<Performer> = emptyList()
        if (jsonObject.has("performers") && !jsonObject.get("performers").isJsonNull) {
            val performersArray = jsonObject.getAsJsonArray("performers")
            Log.d("AlbumTypeAdapter", "Performers count: ${performersArray.size()}")
            val performerListType = object : TypeToken<List<Performer>>() {}.type
            performers = context.deserialize(performersArray, performerListType) ?: emptyList()
        }

        // Deserializar comments
        var comments: List<Comment> = emptyList()
        if (jsonObject.has("comments") && !jsonObject.get("comments").isJsonNull) {
            val commentsArray = jsonObject.getAsJsonArray("comments")
            Log.d("AlbumTypeAdapter", "Comments count: ${commentsArray.size()}")
            val commentListType = object : TypeToken<List<Comment>>() {}.type
            comments = context.deserialize(commentsArray, commentListType) ?: emptyList()
        }

        // Crear el Album y asignar los campos
        val album = Album(
            id = id,
            name = name,
            description = description,
            cover = cover,
            releaseDate = releaseDate,
            genre = genre,
            recordLabel = recordLabel,
            artists = artists,
            tracksCount = tracks.size,
            performersCount = performers.size,
            commentsCount = comments.size
        )

        album.tracks = tracks
        album.performers = performers
        album.comments = comments

        Log.d("AlbumTypeAdapter", "Album deserialized: tracks=${album.tracks.size}, performers=${album.performers.size}, comments=${album.comments.size}")

        return album
    }
}

