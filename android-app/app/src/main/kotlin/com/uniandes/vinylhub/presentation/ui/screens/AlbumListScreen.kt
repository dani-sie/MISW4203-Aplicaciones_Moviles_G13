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
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel

@Composable
fun AlbumListScreen(
    viewModel: AlbumViewModel,
    onAlbumClick: (Int) -> Unit
) {
    val albums by viewModel.albums.collectAsState(initial = emptyList())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Álbumes",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (albums.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(albums) { album ->
                    AlbumListItem(
                        album = album,
                        onClick = { onAlbumClick(album.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumListItem(
    album: Album,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = album.name)
        Text(text = "Género: ${album.genre}")
        Text(text = "Sello: ${album.recordLabel}")
    }
}

@Preview(showBackground = true)
@Composable
fun AlbumListItemPreview() {
    val sampleAlbum = Album(
        id = 1,
        name = "Buscando América",
        cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
        releaseDate = "1984-08-01T00:00:00-05:00",
        description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984.",
        genre = "Salsa",
        recordLabel = "Elektra",
        artists = listOf(1, 2)
    )
    AlbumListItem(album = sampleAlbum, onClick = {})
}

