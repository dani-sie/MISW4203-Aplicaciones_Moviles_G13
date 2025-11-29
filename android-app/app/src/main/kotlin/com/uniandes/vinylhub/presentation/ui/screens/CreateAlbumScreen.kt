package com.uniandes.vinylhub.presentation.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.uniandes.vinylhub.R
import com.uniandes.vinylhub.presentation.viewmodel.AlbumViewModel
import com.uniandes.vinylhub.ui.theme.VinylHubTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlbumScreen(
    viewModel: AlbumViewModel?,
    onBackClick: () -> Unit,
    onAlbumCreated: (Int) -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var albumTitle by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var coverUrl by remember { mutableStateOf("") }
    var recordLabel by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var genreExpanded by remember { mutableStateOf(false) }
    var recordLabelExpanded by remember { mutableStateOf(false) }
    var isCreating by remember { mutableStateOf(false) }

    val genres = listOf("Classical", "Salsa", "Rock", "Folk")
    val recordLabels = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.title_create_album)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick, enabled = !isCreating) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = context.getString(R.string.button_back)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título del álbum
            OutlinedTextField(
                value = albumTitle,
                onValueChange = { albumTitle = it },
                label = { Text(context.getString(R.string.label_album_title)) },
                placeholder = { Text(context.getString(R.string.hint_album_title)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
            
            // Fecha de lanzamiento
            OutlinedTextField(
                value = releaseDate,
                onValueChange = { releaseDate = it },
                label = { Text(context.getString(R.string.label_release_date)) },
                placeholder = { Text("mm/dd/yyyy") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
            
            // Portada (URL)
            OutlinedTextField(
                value = coverUrl,
                onValueChange = { coverUrl = it },
                label = { Text(context.getString(R.string.label_cover_url)) },
                placeholder = { Text(context.getString(R.string.hint_cover_url)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )
            
            Text(
                text = context.getString(R.string.cover_url_note),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 4.dp)
            )
            
            // Sello discográfico (Dropdown)
            ExposedDropdownMenuBox(
                expanded = recordLabelExpanded,
                onExpandedChange = { recordLabelExpanded = !recordLabelExpanded }
            ) {
                OutlinedTextField(
                    value = recordLabel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(context.getString(R.string.label_record_label)) },
                    placeholder = { Text(context.getString(R.string.hint_record_label)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = recordLabelExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )

                ExposedDropdownMenu(
                    expanded = recordLabelExpanded,
                    onDismissRequest = { recordLabelExpanded = false }
                ) {
                    recordLabels.forEach { label ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                recordLabel = label
                                recordLabelExpanded = false
                            }
                        )
                    }
                }
            }
            
            // Género (Dropdown)
            ExposedDropdownMenuBox(
                expanded = genreExpanded,
                onExpandedChange = { genreExpanded = !genreExpanded }
            ) {
                OutlinedTextField(
                    value = selectedGenre,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(context.getString(R.string.label_genre)) },
                    placeholder = { Text(context.getString(R.string.select_genre)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(8.dp)
                )

                ExposedDropdownMenu(
                    expanded = genreExpanded,
                    onDismissRequest = { genreExpanded = false }
                ) {
                    genres.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(genre) },
                            onClick = {
                                selectedGenre = genre
                                genreExpanded = false
                            }
                        )
                    }
                }
            }

            // Descripción
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(context.getString(R.string.label_description)) },
                placeholder = { Text(context.getString(R.string.hint_description)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                minLines = 4,
                maxLines = 6,
                shape = RoundedCornerShape(8.dp)
            )

            // Botón Crear álbum
            Button(
                onClick = {
                    if (viewModel != null) {
                        isCreating = true
                        coroutineScope.launch {
                            try {
                                val createdAlbum = viewModel.createAlbum(
                                    name = albumTitle,
                                    cover = coverUrl,
                                    releaseDate = releaseDate,
                                    description = description,
                                    genre = selectedGenre,
                                    recordLabel = recordLabel
                                )

                                if (createdAlbum != null) {
                                    // Navegar al detalle del álbum creado
                                    onAlbumCreated(createdAlbum.id)
                                } else {
                                    snackbarHostState.showSnackbar("Error al crear el álbum")
                                    isCreating = false
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                                isCreating = false
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = !isCreating &&
                         albumTitle.isNotBlank() &&
                         releaseDate.isNotBlank() &&
                         coverUrl.isNotBlank() &&
                         recordLabel.isNotBlank() &&
                         selectedGenre.isNotBlank() &&
                         description.isNotBlank()
            ) {
                if (isCreating) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = context.getString(R.string.button_create_album),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // Botón Volver al menú principal
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                border = BorderStroke(1.dp, Color(0xFF1976D2)),
                enabled = !isCreating
            ) {
                Text(
                    text = context.getString(R.string.button_back),
                    color = Color(0xFF1976D2)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAlbumScreenPreview() {
    VinylHubTheme {
        CreateAlbumScreen(
            viewModel = null,
            onBackClick = {}
        )
    }
}

