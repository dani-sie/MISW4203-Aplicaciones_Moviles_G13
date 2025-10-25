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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.data.model.ArtistAlbum
import com.uniandes.vinylhub.data.model.ArtistPrize
import com.uniandes.vinylhub.presentation.viewmodel.ArtistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    artistId: Int,
    viewModel: ArtistViewModel?,
    onBackClick: () -> Unit,
    onAlbumClick: (Int) -> Unit = {}
) {
    val artist = remember { mutableStateOf<Artist?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val error = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(artistId) {
        if (viewModel != null) {
            try {
                isLoading.value = true
                val fetchedArtist = viewModel.getArtistById(artistId)
                artist.value = fetchedArtist
                if (fetchedArtist == null) {
                    error.value = "Artista no encontrado"
                }
            } catch (e: Exception) {
                error.value = "Error al cargar el artista: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artista") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
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
            artist.value != null -> {
                ArtistDetailContent(
                    artist = artist.value!!,
                    onBackClick = onBackClick,
                    onAlbumClick = onAlbumClick,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ArtistDetailContent(
    artist: Artist,
    onBackClick: () -> Unit,
    onAlbumClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {
        item {
            // Artist header with image and info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Artist image - rectangular vertical with rounded corners
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 120.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (artist.image.isNotEmpty()) {
                        AsyncImage(
                            model = artist.image,
                            contentDescription = "Imagen de ${artist.name}",
                            modifier = Modifier
                                .size(width = 80.dp, height = 120.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(2.dp, Color(0xFFDDDDDD), RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("👤", fontSize = 40.sp)
                    }
                }

                // Artist info
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = artist.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Nac.: ${artist.birthDate.substring(0, 10)}",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    // Badges
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        ArtistDetailBadgeChip(
                            label = "${artist.albumsCount} álbum${if (artist.albumsCount != 1) "es" else ""}",
                            backgroundColor = Color(0xFF2196F3)
                        )
                        ArtistDetailBadgeChip(
                            label = "${artist.prizesCount} premio${if (artist.prizesCount != 1) "s" else ""}",
                            backgroundColor = Color(0xFFFFC107)
                        )
                    }

                    // Add to favorites button
                    androidx.compose.material3.OutlinedButton(
                        onClick = { /* TODO: Implement add to favorites */ },
                        modifier = Modifier.padding(top = 8.dp),
                        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF1976D2)
                        ),
                        border = BorderStroke(1.dp, Color(0xFF1976D2))
                    ) {
                        Text("Agregar a favoritos", fontSize = 12.sp)
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
                text = artist.description,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 12.dp),
                maxLines = Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )
        }



        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        }

        if (artist.albums.isNotEmpty()) {
            item {
                // Albums section
                Text(
                    text = "ÁLBUMES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(artist.albums) { album ->
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
                        // Album cover
                        if (album.cover.isNotEmpty()) {
                            AsyncImage(
                                model = album.cover,
                                contentDescription = album.name,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.5.dp, Color(0xFFBBBBBB), RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(Color(0xFFCCCCCC), RoundedCornerShape(8.dp))
                                    .border(1.5.dp, Color(0xFFBBBBBB), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📀", fontSize = 24.sp)
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = album.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Text(
                                text = "${album.genre} • ${album.releaseDate.substring(0, 4)}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                            Text(
                                text = album.description,
                                fontSize = 11.sp,
                                color = Color(0xFF666666),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // Ver button
                        Button(
                            onClick = { onAlbumClick(album.id) },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00BCD4)
                            ),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Ver", fontSize = 12.sp)
                        }
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        if (artist.performerPrizes.isNotEmpty()) {
            item {
                // Prizes section
                Text(
                    text = "PREMIOS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(artist.performerPrizes) { prize ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFF9C4)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🏆",
                            fontSize = 24.sp
                        )
                        Text(
                            text = prize.premiationDate.substring(0, 10),
                            fontSize = 13.sp,
                            color = Color(0xFF333333)
                        )
                    }
                }
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            }
        }

        item {
            // Back to catalog button
            androidx.compose.material3.OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF1976D2)
                ),
                border = BorderStroke(1.dp, Color(0xFF1976D2))
            ) {
                Text("Volver a artistas")
            }
        }
    }
}

@Composable
private fun ArtistDetailBadgeChip(
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
            fontSize = 11.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, widthDp = 390, heightDp = 1000)
@Composable
fun ArtistDetailScreenPreview() {
    val sampleArtist = Artist(
        id = 100,
        name = "Rubén Blades Bellido de Luna",
        birthDate = "1948-07-16T00:00:00.000Z",
        image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
        description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño. Ha desarrollado gran parte de su carrera artística en la ciudad de Nueva York.",
        albumsCount = 2,
        prizesCount = 1
    ).apply {
        albums = listOf(
            ArtistAlbum(
                id = 100,
                name = "Buscando América",
                cover = "https://i.pinimg.com/564x/aa/5f/ed/aa5fed7fac61cc8f41d1e79db917a7cd.jpg",
                releaseDate = "1984-08-01T00:00:00.000Z",
                description = "Buscando América es el primer álbum de la banda de Rubén Blades y Seis del Solar lanzado en 1984. La producción, bajo el sello Elektra, fusiona diferentes ritmos musicales tales como la salsa, reggae, rock, y el jazz latino. El disco fue grabado en Eurosound Studios en Nueva York entre mayo y agosto de 1983.",
                genre = "Salsa",
                recordLabel = "Elektra"
            ),
            ArtistAlbum(
                id = 101,
                name = "Poeta del pueblo",
                cover = "https://cdn.shopify.com/s/files/1/0275/3095/products/image_4931268b-7acf-4702-9c55-b2b3a03ed999_1024x1024.jpg",
                releaseDate = "1984-08-01T00:00:00.000Z",
                description = "Recopilación de 27 composiciones del cosmos Blades que los bailadores y melómanos han hecho suyas en estos 40 años de presencia de los ritmos y concordias afrocaribeños en múltiples escenarios internacionales.",
                genre = "Salsa",
                recordLabel = "Elektra"
            )
        )
        performerPrizes = listOf(
            ArtistPrize(
                id = 100,
                premiationDate = "1978-12-10T00:00:00.000Z"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Artista") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        ArtistDetailContent(artist = sampleArtist, onBackClick = {}, onAlbumClick = {}, modifier = Modifier.padding(paddingValues))
    }
}

