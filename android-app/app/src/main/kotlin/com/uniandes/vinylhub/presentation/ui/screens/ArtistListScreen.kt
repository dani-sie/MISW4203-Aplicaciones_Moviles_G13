package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.presentation.viewmodel.ArtistViewModel

@Composable
fun ArtistListScreen(
    viewModel: ArtistViewModel,
    onArtistClick: (Int) -> Unit
) {
    val artists by viewModel.artists.collectAsState(initial = emptyList())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Artistas",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (artists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(artists) { artist ->
                    ArtistListItem(
                        artist = artist,
                        onClick = { onArtistClick(artist.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ArtistListItem(
    artist: Artist,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = artist.name)
        Text(text = "Nacimiento: ${artist.birthDate}")
    }
}

@Preview(showBackground = true)
@Composable
fun ArtistListItemPreview() {
    val sampleArtist = Artist(
        id = 1,
        name = "Rubén Blades Bellido de Luna",
        birthDate = "1948-07-16T00:00:00-05:00"
    )
    ArtistListItem(artist = sampleArtist, onClick = {})
}

