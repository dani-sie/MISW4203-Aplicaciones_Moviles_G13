package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
    onAlbumClick: (Int) -> Unit,
    onBackToHome: () -> Unit = {}
) {
    val albums by if (viewModel != null) {
        viewModel.albums.collectAsState(initial = emptyList())
    } else {
        remember { mutableStateOf(emptyList<Album>()) }
    }

    val expandedAlbumId = remember { mutableStateOf<Int?>(null) }

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
                    val isExpanded = expandedAlbumId.value == album.id
                    AlbumListItem(
                        album = album,
                        isExpanded = isExpanded,
                        onClick = {
                            expandedAlbumId.value = if (isExpanded) null else album.id
                        },
                        onImageClick = {
                            onAlbumClick(album.id)
                        }
                    )
                }
            }
        }

        // Back to home button
        androidx.compose.material3.OutlinedButton(
            onClick = onBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, androidx.compose.ui.graphics.Color(0xFF1976D2))
        ) {
            androidx.compose.material3.Text("Volver al menú principal", color = androidx.compose.ui.graphics.Color(0xFF1976D2))
        }
    }
}

@Composable
fun AlbumListItem(
    album: Album,
    isExpanded: Boolean = false,
    onClick: () -> Unit,
    onImageClick: () -> Unit = {}
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
                    .background(Color.LightGray)
                    .clickable { onImageClick() },
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
                    maxLines = if (isExpanded) Int.MAX_VALUE else 2,
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
                    Text(if (isExpanded) "Ocultar detalles" else "Ver detalles")
                }

                // Expanded content
                if (isExpanded) {
                    ExpandedAlbumContent(album = album)
                }
            }
        }
    }
}

@Composable
fun ExpandedAlbumContent(album: Album) {
    android.util.Log.d("ExpandedAlbumContent", "Album: ${album.name}, tracks=${album.tracks.size}, performers=${album.performers.size}, comments=${album.comments.size}")

    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

    // Tracks section
    if (album.tracks.isNotEmpty()) {
        Text(
            text = "Tracks",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
        ) {
            Column {
                album.tracks.forEachIndexed { index, track ->
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = track.name,
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f),
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = track.duration,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                        }
                        if (index < album.tracks.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }

    // Performers section
    if (album.performers.isNotEmpty()) {
        Text(
            text = "Intérpretes",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
        )
        Column(modifier = Modifier.padding(bottom = 12.dp)) {
            album.performers.forEach { performer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Imagen circular del artista
                    if (!performer.image.isNullOrEmpty()) {
                        AsyncImage(
                            model = performer.image,
                            contentDescription = performer.name,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, Color(0xFFBBBBBB), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(Color(0xFFCCCCCC), CircleShape)
                                .border(1.5.dp, Color(0xFFBBBBBB), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Sin imagen",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = performer.name,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = "Nac.: ${performer.birthDate?.substring(0, 10) ?: "N/A"}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = performer.description,
                            fontSize = 11.sp,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }

    // Comments section
    if (album.comments.isNotEmpty()) {
        Text(
            text = "Comentarios",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp)
        )
        Column(modifier = Modifier.padding(bottom = 12.dp)) {
            album.comments.forEach { comment ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF5F5F5)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = comment.description,
                            fontSize = 12.sp,
                            color = Color(0xFF333333),
                            modifier = Modifier.weight(1f),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color(0xFF4CAF50),
                                    shape = CircleShape
                                )
                                .size(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "★ ${comment.rating}",
                                fontSize = 12.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
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

@Preview(showBackground = true, widthDp = 390, heightDp = 900)
@Composable
fun AlbumListItemExpandedPreview() {
    val sampleAlbum = Album(
        id = 1,
        name = "Buscando América",
        cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
        releaseDate = "1984-08-01T00:00:00-05:00",
        description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984.",
        genre = "Salsa",
        recordLabel = "Elektra",
        artists = listOf(1, 2),
        tracksCount = 3,
        performersCount = 2,
        commentsCount = 2
    ).apply {
        tracks = listOf(
            com.uniandes.vinylhub.data.model.Track(1, "Buscando América", "3:45"),
            com.uniandes.vinylhub.data.model.Track(2, "Desapariciones", "4:12"),
            com.uniandes.vinylhub.data.model.Track(3, "Tiburón", "3:28")
        )
        performers = listOf(
            com.uniandes.vinylhub.data.model.Performer(
                1, "Rubén Blades", "https://via.placeholder.com/40",
                "Cantante y compositor panameño", "1948-07-16", null
            ),
            com.uniandes.vinylhub.data.model.Performer(
                2, "Willie Colón", "https://via.placeholder.com/40",
                "Trombonista puertorriqueño", "1950-04-28", null
            )
        )
        comments = listOf(
            com.uniandes.vinylhub.data.model.Comment(1, "Excelente álbum de salsa", 5),
            com.uniandes.vinylhub.data.model.Comment(2, "Clásico de la música latina", 5)
        )
    }
    Column(modifier = Modifier.padding(4.dp).verticalScroll(rememberScrollState())) {
        AlbumListItem(album = sampleAlbum, onClick = {}, isExpanded = true)
    }
}



