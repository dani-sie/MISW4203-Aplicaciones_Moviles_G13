package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uniandes.vinylhub.R
import com.uniandes.vinylhub.data.model.Album
import com.uniandes.vinylhub.data.model.Track
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssociateTracksScreen(
    albumViewModel: AlbumViewModel?,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // Estados
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }
    var existingTracks by remember { mutableStateOf<List<Track>>(emptyList()) }
    var newTrackTitle by remember { mutableStateOf("") }
    var newTrackDuration by remember { mutableStateOf("") }
    var isLoadingAlbums by remember { mutableStateOf(true) }
    var isLoadingTracks by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var albumDropdownExpanded by remember { mutableStateOf(false) }
    
    // Cargar álbumes al iniciar
    LaunchedEffect(Unit) {
        if (albumViewModel != null) {
            albumViewModel.albums.collect { albumList ->
                albums = albumList
                isLoadingAlbums = false
            }
        }
    }
    
    // Cargar tracks cuando se selecciona un álbum
    LaunchedEffect(selectedAlbum) {
        if (selectedAlbum != null && albumViewModel != null) {
            isLoadingTracks = true
            try {
                existingTracks = albumViewModel.getAlbumTracks(selectedAlbum!!.id)
            } catch (e: Exception) {
                errorMessage = "Error al cargar tracks: ${e.message}"
            } finally {
                isLoadingTracks = false
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.title_associate_tracks)) }
            )
        },
        snackbarHost = {
            if (errorMessage != null || successMessage != null) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = {
                            errorMessage = null
                            successMessage = null
                        }) {
                            Text("OK")
                        }
                    }
                ) {
                    Text(errorMessage ?: successMessage ?: "")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dropdown de álbumes
            Text(
                text = context.getString(R.string.label_select_album),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            
            ExposedDropdownMenuBox(
                expanded = albumDropdownExpanded,
                onExpandedChange = { albumDropdownExpanded = !albumDropdownExpanded }
            ) {
                OutlinedTextField(
                    value = selectedAlbum?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(context.getString(R.string.hint_select_album)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = albumDropdownExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    enabled = !isLoadingAlbums
                )
                
                ExposedDropdownMenu(
                    expanded = albumDropdownExpanded,
                    onDismissRequest = { albumDropdownExpanded = false }
                ) {
                    albums.forEach { album ->
                        DropdownMenuItem(
                            text = { Text(album.name) },
                            onClick = {
                                selectedAlbum = album
                                albumDropdownExpanded = false
                            }
                        )
                    }
                }
            }
            
            // Sección de tracks existentes
            if (selectedAlbum != null) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                
                Text(
                    text = context.getString(R.string.section_existing_tracks),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                if (isLoadingTracks) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (existingTracks.isEmpty()) {
                    Text(
                        text = context.getString(R.string.no_tracks_message),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        existingTracks.forEach { track ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = false,
                                        onCheckedChange = {},
                                        enabled = false
                                    )
                                    Column {
                                        Text(
                                            text = track.name,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                                Text(
                                    text = track.duration,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Sección de agregar nuevo track
                Text(
                    text = context.getString(R.string.section_add_new_track),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                OutlinedTextField(
                    value = newTrackTitle,
                    onValueChange = { newTrackTitle = it },
                    label = { Text(context.getString(R.string.label_track_title)) },
                    placeholder = { Text(context.getString(R.string.hint_track_title)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = newTrackDuration,
                    onValueChange = { newTrackDuration = it },
                    label = { Text(context.getString(R.string.label_track_duration)) },
                    placeholder = { Text(context.getString(R.string.hint_track_duration)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // Botón de guardar asociación
                Button(
                    onClick = {
                        if (newTrackTitle.isNotBlank() && newTrackDuration.isNotBlank()) {
                            scope.launch {
                                isSaving = true
                                errorMessage = null
                                successMessage = null
                                try {
                                    val track = albumViewModel?.addTrackToAlbum(
                                        albumId = selectedAlbum!!.id,
                                        name = newTrackTitle,
                                        duration = newTrackDuration
                                    )
                                    if (track != null) {
                                        successMessage = "Track agregado exitosamente"
                                        newTrackTitle = ""
                                        newTrackDuration = ""
                                        // Recargar tracks
                                        existingTracks = albumViewModel.getAlbumTracks(selectedAlbum!!.id)
                                    } else {
                                        errorMessage = "Error al agregar track"
                                    }
                                } catch (e: Exception) {
                                    errorMessage = "Error: ${e.message}"
                                } finally {
                                    isSaving = false
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = newTrackTitle.isNotBlank() && newTrackDuration.isNotBlank() && !isSaving
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(context.getString(R.string.button_save_association))
                    }
                }
            } else {
                Text(
                    text = context.getString(R.string.select_album_first),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Botón de volver
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(context.getString(R.string.button_back))
            }
        }
    }
}


