package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.uniandes.vinylhub.R
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.model.Comment
import com.uniandes.vinylhub.data.model.Performer
import com.uniandes.vinylhub.data.model.Track
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumId: Int,
    viewModel: AlbumViewModel?,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val album = remember { mutableStateOf<Album?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(albumId) {
        if (viewModel != null) {
            try {
                isLoading.value = true
                val fetchedAlbum = viewModel.getAlbumById(albumId)
                album.value = fetchedAlbum
                if (fetchedAlbum == null) {
                    error.value = "Álbum no encontrado"
                }
            } catch (e: Exception) {
                error.value = "Error al cargar el álbum: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.title_album)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = context.getString(R.string.button_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when {
            isLoading.value -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error.value != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error.value ?: "Error desconocido")
                }
            }
            album.value != null -> {
                AlbumDetailContent(
                    album = album.value!!,
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun AlbumDetailContent(
    album: Album,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        item {
            // Album header with cover and info
            Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Cover image
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (album.cover.isNotEmpty()) {
                    AsyncImage(
                        model = album.cover,
                        contentDescription = "${context.getString(R.string.content_desc_cover_prefix)} ${album.name}",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(context.getString(R.string.content_desc_no_image))
                }
            }

            // Album info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = album.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${album.genre} • ${album.releaseDate.substring(0, 4)} • ${album.recordLabel} • ${album.tracksCount} tracks",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1976D2)
                        )
                    ) {
                        Text("Reproducir")
                    }
                    androidx.compose.material3.OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f),
                        border = BorderStroke(1.dp, Color(0xFF9E9E9E))
                    ) {
                        Text("Guardar", color = Color(0xFF333333))
                    }
                }
            }
        }
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        item {
            // Description section
            Text(
                text = "DESCRIPCIÓN",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = album.description,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 12.dp),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
        }

        item {
            // Genre and Label badges
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DetailBadgeChip(
                    label = "Género: ${album.genre}",
                    backgroundColor = Color(0xFF00BCD4)
                )
                DetailBadgeChip(
                    label = "Sello: ${album.recordLabel}",
                    backgroundColor = Color(0xFF9E9E9E)
                )
            }

            // Release date
            Text(
                text = "Lanzamiento: ${album.releaseDate.substring(0, 10)}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        if (album.tracks.isNotEmpty()) {
            item {
                // Tracks section
                Text(
                    text = "TRACKS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
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
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${index + 1}.",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF333333)
                                    )
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

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        if (album.performers.isNotEmpty()) {
            item {
                // Performers section
                Text(
                    text = "INTÉRPRETES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(album.performers) { performer ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFE0E0E0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Imagen circular del artista
                        if (performer.image.isNotEmpty()) {
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
                                    contentDescription = context.getString(R.string.content_desc_no_image),
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
                                color = Color(0xFF666666),
                                maxLines = Int.MAX_VALUE,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        if (album.comments.isNotEmpty()) {
            item {
                // Comments section
                Text(
                    text = "COMENTARIOS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(album.comments) { comment ->
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

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        item {
            // Comment button
            androidx.compose.material3.OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                border = BorderStroke(1.dp, Color(0xFF1976D2))
            ) {
                Text("Comentar este álbum", color = Color(0xFF1976D2))
            }

            // Back to catalog button
            androidx.compose.material3.OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                border = BorderStroke(1.dp, Color(0xFF1976D2))
            ) {
                Text("Volver al catálogo", color = Color(0xFF1976D2))
            }
        }
    }
}

@Composable
private fun DetailBadgeChip(
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 390, heightDp = 1000)
@Composable
fun AlbumDetailScreenPreview() {
    val sampleAlbum = Album(
        id = 1,
        name = "Buscando América",
        cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
        releaseDate = "1984-08-01T00:00:00-05:00",
        description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
        genre = "Salsa",
        recordLabel = "Elektra",
        artists = listOf(1, 2),
        tracksCount = 2,
        performersCount = 1,
        commentsCount = 1
    ).apply {
        tracks = listOf(
            Track(1, "Decisiones", "5:05"),
            Track(2, "Desapariciones", "6:29")
        )
        performers = listOf(
            Performer(
                1, "Rubén Blades Bellido de Luna", "https://via.placeholder.com/56",
                "Cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en Nueva York.",
                "1948-07-16", null
            )
        )
        comments = listOf(
            Comment(1, "The most relevant album of Ruben Blades", 5)
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Álbum") }, // Preview: usar string hardcodeado
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver" // Preview: usar string hardcodeado
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AlbumDetailContent(album = sampleAlbum, onBackClick = {}, modifier = Modifier.padding(paddingValues))
    }
}

