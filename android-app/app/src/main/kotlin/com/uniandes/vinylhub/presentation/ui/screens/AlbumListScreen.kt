package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel

@Composable
fun AlbumListScreen(
    viewModel: AlbumViewModel?,
    onAlbumClick: (Int) -> Unit
) {
    val albums by if (viewModel != null) {
        viewModel.albums.collectAsState(initial = emptyList())
    } else {
        remember { mutableStateOf(emptyList<Album>()) }
    }

    android.util.Log.d("AlbumListScreen", "Albums received: ${albums.size}")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "🎵 Catálogo",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Search bar
        androidx.compose.material3.TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar álbum o artista") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        if (albums.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Cargando álbumes...",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Cover image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (album.cover.isNotEmpty()) {
                    AsyncImage(
                        model = album.cover,
                        contentDescription = "Cover de ${album.name}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Sin imagen")
                }
            }

            // Content
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = album.name,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${album.genre} • ${album.releaseDate.substring(0, 4)} • ${album.recordLabel}",
                    fontSize = 12.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = album.description,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Badges row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BadgeChip(
                        label = "${album.tracksCount} pista${if (album.tracksCount != 1) "s" else ""}",
                        backgroundColor = Color(0xFF2196F3)
                    )
                    BadgeChip(
                        label = "${album.performersCount} intérprete${if (album.performersCount != 1) "s" else ""}",
                        backgroundColor = Color(0xFF2196F3)
                    )
                    BadgeChip(
                        label = "${album.commentsCount} comentario${if (album.commentsCount != 1) "s" else ""}",
                        backgroundColor = Color(0xFFFFC107)
                    )
                }

                // Ver detalles button
                OutlinedButton(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver detalles")
                }
            }
        }
    }
}

@Composable
fun BadgeChip(
    label: String,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun AlbumListScreenPreview() {
    val sampleAlbums = listOf(
        Album(
            id = 1,
            name = "Buscando América",
            cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
            releaseDate = "1984-08-01T00:00:00-05:00",
            description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984.",
            genre = "Salsa",
            recordLabel = "Elektra",
            artists = listOf(1, 2)
        ),
        Album(
            id = 2,
            name = "Poeta del pueblo",
            cover = "https://cdn.shopify.com/s/files/1/0275/3095/products/image_4931268b-7acf-4702-9c55-b2b3a03ed999_1024x1024.jpg",
            releaseDate = "1984-08-01T00:00:00-05:00",
            description = "Recopilación de 27 composiciones del cosmos Blades que los bailadores y melómanos han hecho suyas.",
            genre = "Salsa",
            recordLabel = "Elektra",
            artists = listOf(1)
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "🎵 Catálogo",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Search bar
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar álbum o artista") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(sampleAlbums) { album ->
                AlbumListItem(
                    album = album,
                    onClick = {}
                )
            }
        }
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
    Column(modifier = Modifier.padding(8.dp)) {
        AlbumListItem(album = sampleAlbum, onClick = {})
    }
}



