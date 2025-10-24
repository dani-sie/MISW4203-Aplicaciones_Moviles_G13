package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uniandes.vinylhub.data.model.Album

@Composable
fun AlbumDetailScreen(
    album: Album,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = album.name,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(text = "Descripción:")
        Text(
            text = album.description,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(text = "Género: ${album.genre}")
        Text(text = "Sello: ${album.recordLabel}")
        Text(text = "Fecha de lanzamiento: ${album.releaseDate}")
        
        if (album.artists.isNotEmpty()) {
            Text(
                text = "Artistas: ${album.artists.joinToString(", ")}",
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

