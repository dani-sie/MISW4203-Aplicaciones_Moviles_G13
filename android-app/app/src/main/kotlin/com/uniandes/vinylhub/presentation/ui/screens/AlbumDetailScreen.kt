package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    albumId: Int,
    viewModel: AlbumViewModel?,
    onBackClick: () -> Unit
) {
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
                title = { Text(album.value?.name ?: "Detalle del Álbum") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        androidx.compose.material3.Icon(
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
            album.value != null -> {
                AlbumDetailContent(
                    album = album.value!!,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun AlbumDetailContent(
    album: Album,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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

