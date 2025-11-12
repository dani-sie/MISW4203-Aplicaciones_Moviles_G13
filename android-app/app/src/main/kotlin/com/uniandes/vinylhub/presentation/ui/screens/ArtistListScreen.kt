package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.uniandes.vinylhub.R
import com.uniandes.vinylhub.data.model.Artist
import com.uniandes.vinylhub.presentation.viewmodel.ArtistViewModel

@Composable
fun ArtistListScreen(
    viewModel: ArtistViewModel,
    onArtistClick: (Int) -> Unit,
    onBackToHome: () -> Unit = {}
) {
    val context = LocalContext.current
    val artists: List<Artist> by viewModel.artists.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = context.getString(R.string.title_artists),
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Search bar
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar artista") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        if (artists.isEmpty()) {
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
                        text = context.getString(R.string.loading_artists),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(artists) { artist ->
                    ArtistListItem(
                        artist = artist,
                        onClick = { onArtistClick(artist.id) }
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
fun ArtistListItem(
    artist: Artist,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Artist image - rectangular vertical with rounded corners
        Box(
            modifier = Modifier
                .size(width = 56.dp, height = 80.dp)
                .border(
                    width = 1.dp,
                    color = androidx.compose.ui.graphics.Color(0xFFDDDDDD),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (artist.image.isNotEmpty()) {
                AsyncImage(
                    model = artist.image,
                    contentDescription = artist.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text("📀", fontSize = 24.sp)
            }
        }

        // Artist info
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = artist.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Nac.: ${artist.birthDate.substring(0, 10)}",
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color(0xFF666666),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Badges row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ArtistBadgeChip(
                    label = "${artist.albumsCount} álbum${if (artist.albumsCount != 1) "es" else ""}",
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFF2196F3)
                )
                ArtistBadgeChip(
                    label = "${artist.prizesCount} premio${if (artist.prizesCount != 1) "s" else ""}",
                    backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFC107)
                )
            }
        }

        // Details button with gray border and text
        androidx.compose.material3.OutlinedButton(
            onClick = onClick,
            modifier = Modifier.padding(start = 8.dp),
            border = BorderStroke(1.dp, androidx.compose.ui.graphics.Color(0xFF9E9E9E))
        ) {
            Text(context.getString(R.string.button_details), color = androidx.compose.ui.graphics.Color(0xFF9E9E9E))
        }
    }
}

@Composable
fun ArtistBadgeChip(
    label: String,
    backgroundColor: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 3.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = androidx.compose.ui.graphics.Color.White
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 600)
@Composable
fun ArtistListScreenPreview() {
    val sampleArtists = listOf(
        Artist(
            id = 100,
            name = "Rubén Blades Bellido de Luna",
            birthDate = "1948-07-16T00:00:00.000Z",
            image = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bb/Ruben_Blades_by_Gage_Skidmore.jpg/800px-Ruben_Blades_by_Gage_Skidmore.jpg",
            description = "Es un cantante, compositor, músico, actor, abogado, político y activista panameño.",
            albumsCount = 2,
            prizesCount = 1
        ),
        Artist(
            id = 1,
            name = "Willie Colón",
            birthDate = "1950-04-28T00:00:00.000Z",
            image = "https://via.placeholder.com/56",
            description = "Trombonista puertorriqueño",
            albumsCount = 3,
            prizesCount = 2
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "👥 Artistas", // Preview: usar string hardcodeado
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Buscar artista") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(24.dp),
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(sampleArtists) { artist ->
                ArtistListItem(
                    artist = artist,
                    onClick = {}
                )
            }
        }
    }
}

